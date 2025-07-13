// androidlead.weatherappui.ui.data.repository/WeatherRepositoryImpl.kt
package androidlead.weatherappui.ui.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import androidlead.weatherappui.ui.data.local.dao.WeatherDao
import androidlead.weatherappui.ui.data.mapper.toCurrentWeather
import androidlead.weatherappui.ui.data.mapper.toCurrentWeatherEntity
import androidlead.weatherappui.ui.data.mapper.toDailyForecast
import androidlead.weatherappui.ui.data.remote.api.WeatherApiService // تغییر به WeatherApiService
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import androidlead.weatherappui.ui.domain.util.Resource
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.map // برای استفاده از map در Flow

// پیاده‌سازی اینترفیس WeatherRepository
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService, // تغییر به WeatherApiService
    private val dao: WeatherDao // DAO برای داده‌های دیتابیس محلی
) : WeatherRepository {

    // کلید API را از NetworkModule دریافت نمی‌کنیم، بلکه مستقیماً اینجا استفاده می‌کنیم (یا از یک BuildConfig)
    // برای سادگی، فرض می‌کنیم کلید API در NetworkModule به درخواست‌ها اضافه می‌شود.
    // اگر نیاز به ارسال دستی کلید در هر درخواست دارید، باید آن را به این توابع پاس دهید.
    private val API_KEY = "YOUR_WEATHERAPI_KEY" // اینجا هم کلید API را قرار دهید یا از BuildConfig بخوانید

    // دریافت آب و هوای فعلی
    override fun getCurrentWeather(
        location: String
    ): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading()) // شروع بارگذاری

        // ابتدا از دیتابیس محلی تلاش می‌کنیم
        val localWeatherFlow = dao.getCurrentWeather()
        var localWeather: CurrentWeather? = null
        localWeatherFlow.collect { entity ->
            entity?.let {
                localWeather = it.toCurrentWeather()
                if (!isDataStale(it.timestamp)) {
                    emit(Resource.Success(localWeather!!)) // داده‌های محلی موجود و تازه هستند
                }
            }
        }


        try {
            // سپس از API تلاش می‌کنیم
            val response = api.getCurrentWeather(API_KEY, location, "yes") // "yes" برای کیفیت هوا
            if (response.isSuccessful && response.body() != null) {
                val remoteWeatherDto = response.body()!!
                // تبدیل DTO به مدل دامین
                val remoteWeather = remoteWeatherDto.toCurrentWeather()
                // ذخیره در دیتابیس محلی
                dao.deleteCurrentWeather() // حذف رکورد قبلی
                dao.insertCurrentWeather(remoteWeatherDto.toCurrentWeatherEntity(location))
                emit(Resource.Success(remoteWeather)) // ارسال داده‌های تازه
            } else {
                // پاسخ ناموفق از API
                val errorMessage = response.errorBody()?.string() ?: "خطای ناشناخته API"
                emit(Resource.Error("خطا: $errorMessage", localWeather))
            }
        } catch (e: IOException) {
            // خطای شبکه
            emit(Resource.Error("خطای شبکه: ${e.localizedMessage}", localWeather))
        } catch (e: Exception) {
            // خطاهای دیگر (مثل خطای JSON parsing)
            emit(Resource.Error("خطایی رخ داد: ${e.localizedMessage}", localWeather))
        }
    }

    // دریافت پیش‌بینی 5 روزه
    override fun getFiveDayForecast(
        location: String
    ): Flow<Resource<List<DailyForecast>>> = flow {
        emit(Resource.Loading()) // شروع بارگذاری

        // ابتدا از دیتابیس محلی تلاش می‌کنیم
        val localForecastsFlow = dao.getDailyForecasts()
        var localForecasts: List<DailyForecast>? = null
        localForecastsFlow.collect { entities ->
            if (entities.isNotEmpty()) {
                localForecasts = entities.map { it.toDailyForecast() }
                if (!isDataStale(entities.first().timestamp)) {
                    emit(Resource.Success(localForecasts!!)) // داده‌های محلی موجود و تازه هستند
                }
            }
        }

        try {
            // سپس از API تلاش می‌کنیم
            val response = api.getForecastWeather(API_KEY, location, 5, "yes") // 5 روز پیش‌بینی
            if (response.isSuccessful && response.body() != null) {
                val remoteForecastDto = response.body()!!
                // تبدیل DTO به مدل دامین
                val remoteForecasts = remoteForecastDto.toDailyForecasts()
                // ذخیره در دیتابیس محلی
                dao.deleteDailyForecasts() // حذف رکوردهای قبلی
                dao.insertDailyForecasts(remoteForecastDto.forecast.forecastday.map { forecastDay ->
                    // تبدیل ForecastDay DTO به DailyForecastEntity
                    DailyForecastEntity(
                        dateEpochMillis = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(forecastDay.date)?.time ?: 0L,
                        dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(forecastDay.date)),
                        date = forecastDay.date,
                        maxTemp = forecastDay.day.maxtemp_c,
                        minTemp = forecastDay.day.mintemp_c,
                        avgTemp = forecastDay.day.avgtemp_c,
                        dailyChanceOfRain = forecastDay.day.daily_chance_of_rain,
                        description = forecastDay.day.condition.text,
                        icon = forecastDay.day.condition.icon.replace("//", "https://"),
                        timestamp = System.currentTimeMillis()
                    )
                })
                emit(Resource.Success(remoteForecasts)) // ارسال داده‌های تازه
            } else {
                // پاسخ ناموفق از API
                val errorMessage = response.errorBody()?.string() ?: "خطای ناشناخته API"
                emit(Resource.Error("خطا: $errorMessage", localForecasts))
            }
        } catch (e: IOException) {
            // خطای شبکه
            emit(Resource.Error("خطای شبکه: ${e.localizedMessage}", localForecasts))
        } catch (e: Exception) {
            // خطاهای دیگر
            emit(Resource.Error("خطایی رخ داد: ${e.localizedMessage}", localForecasts))
        }
    }

    // بررسی که آیا داده‌ها قدیمی شده‌اند (مثلاً بیشتر از 30 دقیقه)
    private fun isDataStale(timestamp: Long): Boolean {
        val thirtyMinutesInMillis = 30 * 60 * 1000 // 30 دقیقه
        return System.currentTimeMillis() - timestamp > thirtyMinutesInMillis
    }
}
