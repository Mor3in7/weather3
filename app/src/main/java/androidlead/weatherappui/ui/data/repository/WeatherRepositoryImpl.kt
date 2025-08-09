package androidlead.weatherappui.ui.data.repository

import androidlead.weatherappui.ui.data.local.dao.WeatherDao
import androidlead.weatherappui.ui.data.mapper.*
import androidlead.weatherappui.ui.data.remote.api.WeatherApiService
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
    private val dao: WeatherDao
) : WeatherRepository {

    override fun getWeatherData(location: String): Flow<Resource<Pair<CurrentWeather, List<DailyForecast>>>> = flow {
        emit(Resource.Loading())

        val currentWeatherEntity = dao.getCurrentWeather(location).first()
        val forecastEntities = dao.getDailyForecasts(location).first()

        val localCurrent = currentWeatherEntity?.toCurrentWeather()
        val localForecast = forecastEntities.map { it.toDailyForecast() }

        val isCurrentValid = currentWeatherEntity?.timestamp?.let { !isDataStale(it) } ?: false
        val isForecastValid = forecastEntities.firstOrNull()?.timestamp?.let { !isDataStale(it) } ?: false

        if (isCurrentValid && isForecastValid) {
            emit(Resource.Success(Pair(localCurrent!!, localForecast)))
        }

        try {
            val response = api.getWeatherData(location)

            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!

                val current = dto.toCurrentWeather()
                val forecast = dto.toDailyForecasts()

                dao.saveWeatherData(
                    current.toCurrentWeatherEntity(),
                    forecast.map { it.toDailyForecastEntity() }
                )

                emit(Resource.Success(Pair(current, forecast)))
            } else {
                emit(Resource.Error("API Error: ${response.code()} ${response.message()}",
                    if (localCurrent != null) Pair(localCurrent, localForecast) else null
                ))
            }

        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.message}",
                if (localCurrent != null) Pair(localCurrent, localForecast) else null
            ))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}",
                if (localCurrent != null) Pair(localCurrent, localForecast) else null
            ))
        }
    }

    private fun isDataStale(timestamp: Long): Boolean {
        val THIRTY_MIN = 30 * 60 * 1000L
        return System.currentTimeMillis() - timestamp > THIRTY_MIN
    }
}
