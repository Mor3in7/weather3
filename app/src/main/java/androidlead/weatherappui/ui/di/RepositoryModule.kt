package androidlead.weatherappui.ui.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import androidlead.weatherappui.ui.data.repository.WeatherRepositoryImpl
import androidlead.weatherappui.ui.domain.repository.WeatherRepository
import javax.inject.Singleton

// Hilt Module for providing Repository dependencies
@Module
@InstallIn(SingletonComponent::class) // This module is installed at the Singleton Component level
abstract class RepositoryModule { // Removed the dot before class

    // Binds WeatherRepositoryImpl to the WeatherRepository interface
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
