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
    // Operator function allows calling the use case like a function (e.g., getWeatherUseCase("Tehran"))
    operator fun invoke(location: String): Flow<Result<Pair<CurrentWeather, List<DailyForecast>>>> {
        return repository.getWeatherForecast(location)
    }


}
