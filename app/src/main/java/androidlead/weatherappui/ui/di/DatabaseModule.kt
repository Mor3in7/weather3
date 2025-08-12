package androidlead.weatherappui.ui.di

import android.content.Context
import androidx.room.Room
import androidlead.weatherappui.ui.data.local.WeatherDatabase
import androidlead.weatherappui.ui.data.local.dao.UserDao
import androidlead.weatherappui.ui.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao = db.weatherDao()

    @Provides
    fun provideUserDao(db: WeatherDatabase): UserDao = db.userDao()
}
