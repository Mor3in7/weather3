package androidlead.weatherappui.ui.domain.repository

import kotlinx.coroutines.flow.Flow
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.util.Resource

// اینترفیس Repository برای عملیات مربوط به آب و هوا
interface WeatherRepository {
    // دریافت آب و هوای فعلی بر اساس مکان (نام شهر، مختصات و...)

    // دریافت پیش‌بینی 5 روزه بر اساس مکان
    fun getFiveDayForecast(location: String): Flow<Result<Pair<List<DailyForecast>,CurrentWeather>>>
}
