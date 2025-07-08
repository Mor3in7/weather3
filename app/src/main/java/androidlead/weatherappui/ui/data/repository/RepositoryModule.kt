package androidlead.weatherappui.ui.data.repository

// di/RepositoryModule.kt
package your.app.package.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import your.app.package.data.repository.WeatherRepositoryImpl
import your.app.package.domain.repository.WeatherRepository
import javax.inject.Singleton

// ماژول Hilt برای ارائه وابستگی‌های مربوط به Repository
@Module
@InstallIn(SingletonComponent::class) // این ماژول در سطح Singleton Component نصب می‌شود
abstract class RepositoryModule {

    // اتصال WeatherRepositoryImpl به اینترفیس WeatherRepository
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
