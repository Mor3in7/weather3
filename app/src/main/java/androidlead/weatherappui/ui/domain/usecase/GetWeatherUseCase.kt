package androidlead.weatherappui.ui.domain.usecase

import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase برای دریافت اطلاعات آب‌وهوا (وضعیت فعلی + پیش‌بینی)
 */
class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    /**
     * اجرای مستقیم use case با `invoke("Tehran")`
     */
    operator fun invoke(location: String): Flow<Resource<Pair<CurrentWeather, List<DailyForecast>>>> {
        return repository.getWeatherData(location)
    }
}
