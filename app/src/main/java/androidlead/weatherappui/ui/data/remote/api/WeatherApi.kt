package androidlead.weatherappui.ui.data.remote.api

// data/remote/api/WeatherApi.kt
import androidlead.weatherappui.ui.data.remote.dto.CurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query
import androidlead.weatherappui.ui.data.remote.dto.current_weather.CurrentWeatherDto
import androidlead.weatherappui.ui.data.remote.dto.five_day_forecast.FiveDayForecastDto

// اینترفیس Retrofit برای فراخوانی‌های API آب و هوا
interface WeatherApi {

    // دریافت آب و هوای فعلی بر اساس طول و عرض جغرافیایی
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double, // عرض جغرافیایی
        @Query("lon") lon: Double, // طول جغرافیایی
        @Query("units") units: String = "metric", // واحد دما (metric برای سلسیوس)
        @Query("lang") lang: String = "fa" // زبان پاسخ (فارسی)
    ): CurrentWeatherDto

    // دریافت پیش‌بینی 5 روزه / 3 ساعته بر اساس طول و عرض جغرافیایی
    @GET("forecast")
    suspend fun getFiveDayForecast(
        @Query("lat") lat: Double, // عرض جغرافیایی
        @Query("lon") lon: Double, // طول جغرافیایی
        @Query("units") units: String = "metric", // واحد دما (metric برای سلسیوس)
        @Query("lang") lang: String = "fa" // زبان پاسخ (فارسی)
    ): FiveDayForecastDto

    // دریافت آب و هوای فعلی بر اساس نام شهر
    @GET("weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") cityName: String, // نام شهر
        @Query("units") units: String = "metric", // واحد دما (metric برای سلسیوس)
        @Query("lang") lang: String = "fa" // زبان پاسخ (فارسی)
    ): CurrentWeatherDto

    // دریافت پیش‌بینی 5 روزه / 3 ساعته بر اساس نام شهر
    @GET("forecast")
    suspend fun getFiveDayForecastByCityName(
        @Query("q") cityName: String, // نام شهر
        @Query("units") units: String = "metric", // واحد دما (metric برای سلسیوس)
        @Query("lang") lang: String = "fa" // زبان پاسخ (فارسی)
    ): FiveDayForecastDto
}
