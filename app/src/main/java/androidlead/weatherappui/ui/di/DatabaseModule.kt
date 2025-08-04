package androidlead.weatherappui.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidlead.weatherappui.ui.data.remote.api.WeatherApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // This module is installed at the Singleton Component level
object NetworkModule {

    // WeatherAPI.com API key - In a real project, it's better to store this in local.properties
    // and access it via BuildConfig.
    private const val WEATHER_API_KEY = "af90d1f1ee1e47608e0105059250807"
    private const val BASE_URL = "https://api.weatherapi.com/"

    // Provides HttpLoggingInterceptor for logging network requests and responses
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log full request and response details
        }
    }

    // Provides OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add logging interceptor
            .addInterceptor { chain ->
                // Add API key to all requests
                val originalRequest = chain.request()
                val newUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("key", WEATHER_API_KEY) // Add API key as a query parameter
                    .build()
                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    // Provides Retrofit instance
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // Set base URL
            .client(okHttpClient) // Set OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON conversion
            .build()
    }

    // Provides WeatherApiService
    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java) // Create API interface
    }
}
