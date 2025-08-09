package androidlead.weatherappui.ui.data.remote.api

import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

import retrofit2.Response



    interface WeatherApiService {

        @GET("v1/forecast.json")
        suspend fun getWeatherData(
            @Query("q") location: String,           // شهر یا مختصات
            @Query("days") days: Int = 6,           // پیش‌بینی ۶ روز
            @Query("aqi") aqi: String = "yes"       // اطلاعات کیفیت هوا
        ): Response<WeatherDto>
    }

