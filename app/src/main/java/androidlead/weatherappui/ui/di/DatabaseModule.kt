package androidlead.weatherappui.ui.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidlead.weatherappui.ui.data.remote.api.WeatherApiService // تغییر به WeatherApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // این ماژول در سطح Singleton Component نصب می‌شود
object NetworkModule {

    // کلید API WeatherAPI.com - این را باید با کلید خودتان جایگزین کنید
    // در یک پروژه واقعی، بهتر است این را در فایل local.properties ذخیره کنید و از طریق BuildConfig به آن دسترسی داشته باشید.
    private const val WEATHER_API_KEY = "af90d1f1ee1e47608e0105059250807" // تغییر نام به WEATHER_API_KEY
    private const val BASE_URL = "https://api.weatherapi.com/" // تغییر BASE_URL به WeatherAPI.com

    // ارائه دهنده HttpLoggingInterceptor برای لاگ کردن درخواست‌ها و پاسخ‌های شبکه
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // لاگ کردن جزئیات کامل درخواست و پاسخ
        }
    }

    // ارائه دهنده OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // افزودن اینترسپتور لاگ
            .addInterceptor { chain ->
                // افزودن کلید API به تمام درخواست‌ها
                val originalRequest = chain.request()
                val newUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("key", WEATHER_API_KEY) // تغییر پارامتر به "key"
                    .build()
                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    // ارائه دهنده Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // تنظیم URL پایه
            .client(okHttpClient) // تنظیم OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // استفاده از Gson برای تبدیل JSON
            .build()
    }

    // ارائه دهنده WeatherApiService
    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService { // تغییر نام تابع و نوع بازگشتی
        return retrofit.create(WeatherApiService::class.java) // ساخت اینترفیس API
    }
}
