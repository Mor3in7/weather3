package androidlead.weatherappui.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import androidlead.weatherappui.ui.data.remote.api.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton
import androidlead.weatherappui.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.weatherapi.com/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request()
                val newUrl = request.url.newBuilder()
                    .addQueryParameter("key", BuildConfig.WEATHER_API_KEY)
                    .build()
                chain.proceed(request.newBuilder().url(newUrl).build())
            }
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)
}
