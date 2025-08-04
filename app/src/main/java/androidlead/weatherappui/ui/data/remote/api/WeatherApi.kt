package androidlead.weatherappui.ui.data.remote.api

import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

import retrofit2.Response


interface WeatherApiService {

    @GET("v1/current.json") // فرض می‌کنیم endpoint برای آب و هوای فعلی این باشه
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String, // می‌تونه نام شهر، کد پستی، مختصات و... باشه
        @Query("aqi") aqi: String = "yes",// اگر نیاز به اطلاعات کیفیت هوا داری، میتونی "yes" بذاری
    ): Response<WeatherDto>

        @GET("v1/forecast.json")
    suspend fun getForecastWeather(
            @Query("dt") date: String, // تاریخ به فرمت YYYY-MM-DD
            @Query("key") apiKey: String,
            @Query("q") location: String,
            @Query("days") days: Int = 6,
            @Query("aqi") aqi: String = "yes"
    ): Response<WeatherDto>
}
