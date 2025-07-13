package androidlead.weatherappui.ui.domain.usecase

import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource
import javax.inject.Inject

// Use Case برای دریافت تمام اطلاعات آب و هوا (فعلی و پیش‌بینی)
class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    // فراخوانی Use Case برای آب و هوای فعلی
    operator fun invoke(location: String): Flow<Resource<CurrentWeather>> {
        return repository.getCurrentWeather(location)
    }

    // فراخوانی Use Case برای پیش‌بینی 5 روزه
    fun getFiveDayForecast(location: String): Flow<Resource<List<DailyForecast>>> {
        return repository.getFiveDayForecast(location)
    }
}
