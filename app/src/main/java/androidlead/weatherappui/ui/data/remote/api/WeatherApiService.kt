package androidlead.weatherappui.ui.data.remote.api

import androidlead.weatherappui.ui.data.remote.dto.CitySearchItemDto
import androidlead.weatherappui.ui.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface WeatherApiService {

    @GET("v1/forecast.json")
    suspend fun getWeatherData(
        @Query("q") location: String,
        @Query("days") days: Int = 6,
        @Query("aqi") aqi: String = "yes"
    ): Response<WeatherDto>

    @GET("v1/search.json")
    suspend fun searchCities(
        @Query("q") query: String
    ): Response<List<CitySearchItemDto>>
}
