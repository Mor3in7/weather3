package androidlead.weatherappui.ui.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import androidlead.weatherappui.ui.data.local.dao.WeatherDao
import androidlead.weatherappui.ui.data.mapper.toCurrentWeather
import androidlead.weatherappui.ui.data.mapper.toCurrentWeatherEntity
import androidlead.weatherappui.ui.data.mapper.toDailyForecast
import androidlead.weatherappui.ui.data.mapper.toDailyForecastEntity
import androidlead.weatherappui.ui.data.mapper.toDailyForecasts
import androidlead.weatherappui.ui.data.remote.api.WeatherApiService
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.first // To get the first emitted value from a Flow

// Implementation of the WeatherRepository interface
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
    private val dao: WeatherDao
) : WeatherRepository {

    // No need for API_KEY here, it's handled by the NetworkModule interceptor

    // Get current weather and 5-day forecast
    override fun getCurrentAndFiveDayForecast(
        location: String
    ): Flow<Resource<Pair<CurrentWeather, List<DailyForecast>>>> = flow {
        emit(Resource.Loading()) // Start loading

        // Try to load from local database first
        val localCurrentWeatherFlow = dao.getCurrentWeather(location)
        val localDailyForecastsFlow = dao.getDailyForecasts(location)

        var localCurrentWeather: CurrentWeather? = null
        var localDailyForecasts: List<DailyForecast>? = null

        // Collect the first available local data
        val currentWeatherEntity = localCurrentWeatherFlow.first()
        val dailyForecastEntities = localDailyForecastsFlow.first()

        currentWeatherEntity?.let {
            localCurrentWeather = it.toCurrentWeather()
        }
        if (dailyForecastEntities.isNotEmpty()) {
            localDailyForecasts = dailyForecastEntities.map { it.toDailyForecast() }
        }

        // If local data exists and is not stale, emit it as success
        if (localCurrentWeather != null && localDailyForecasts != null &&
            !isDataStale(currentWeatherEntity!!.timestamp) &&
            !isDataStale(dailyForecastEntities.first().timestamp)
        ) {
            emit(Resource.Success(Pair(localCurrentWeather!!, localDailyForecasts!!)))
        }

        try {
            // Then try to fetch from API
            val currentResponse = api.getCurrentWeather(location, "yes")
            val forecastResponse = api.getForecastWeather(location, "af90d1f1ee1e47608e0105059250807", "yes") // 6 days forecast (today + 5 future)

            if (currentResponse.isSuccessful && currentResponse.body() != null &&
                forecastResponse.isSuccessful && forecastResponse.body() != null
            ) {
                val remoteCurrentWeatherDto = currentResponse.body()!!
                val remoteForecastWeatherDto = forecastResponse.body()!!

                // Convert DTOs to domain models
                val remoteCurrentWeather = remoteCurrentWeatherDto.toCurrentWeather()
                val remoteDailyForecasts = remoteForecastWeatherDto.toDailyForecasts()

                // Save to local database in a transaction
                dao.saveWeatherData(
                    remoteCurrentWeather.toCurrentWeatherEntity(),
                    remoteDailyForecasts.map { it.toDailyForecastEntity() }
                )

                emit(Resource.Success(Pair(remoteCurrentWeather, remoteDailyForecasts))) // Emit fresh data
            } else {
                // Unsuccessful API response
                val currentErrorMessage = currentResponse.errorBody()?.string() ?: "Unknown API error for current weather"
                val forecastErrorMessage = forecastResponse.errorBody()?.string() ?: "Unknown API error for forecast"
                val combinedErrorMessage = "Current Weather Error: $currentErrorMessage\nForecast Error: $forecastErrorMessage"
                emit(Resource.Error(combinedErrorMessage,
                    if (localCurrentWeather != null && localDailyForecasts != null) Pair(localCurrentWeather!!, localDailyForecasts!!) else null
                ))
            }
        } catch (e: IOException) {
            // Network error
            emit(Resource.Error("Network error: ${e.localizedMessage}",
                if (localCurrentWeather != null && localDailyForecasts != null) Pair(localCurrentWeather!!, localDailyForecasts!!) else null
            ))
        } catch (e: Exception) {
            // Other errors (e.g., JSON parsing error)
            emit(Resource.Error("An error occurred: ${e.localizedMessage}",
                if (localCurrentWeather != null && localDailyForecasts != null) Pair(localCurrentWeather!!, localDailyForecasts!!) else null
            ))
        }
    }

    // Check if data is stale (e.g., older than 30 minutes)
    private fun isDataStale(timestamp: Long): Boolean {
        val thirtyMinutesInMillis = 30 * 60 * 1000L // 30 minutes in milliseconds
        return System.currentTimeMillis() - timestamp > thirtyMinutesInMillis
    }
}
