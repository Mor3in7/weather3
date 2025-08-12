package androidlead.weatherappui.ui.data.local

import androidlead.weatherappui.ui.data.local.dao.UserDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidlead.weatherappui.ui.data.local.dao.WeatherDao
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity
import androidlead.weatherappui.ui.data.local.entity.UserEntity

// Room database definition
@Database(
    entities = [CurrentWeatherEntity::class, DailyForecastEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao
}
