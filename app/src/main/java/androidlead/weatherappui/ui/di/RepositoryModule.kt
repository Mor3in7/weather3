package androidlead.weatherappui.ui.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import androidlead.weatherappui.ui.data.repository.WeatherRepositoryImpl
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import javax.inject.Singleton

// ماژول Hilt برای ارائه وابستگی‌های مربوط به Repository
@Module
@InstallIn(SingletonComponent::class) // این ماژول در سطح Singleton Component نصب می‌شود
abstract class.RepositoryModule {

    // اتصال WeatherRepositoryImpl به اینترفیس WeatherRepository
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
