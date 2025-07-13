package androidlead.weatherappui.ui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidlead.weatherappui.ui.data.local.dao.WeatherDao
import androidlead.weatherappui.ui.data.local.entity.CurrentWeatherEntity
import androidlead.weatherappui.ui.data.local.entity.DailyForecastEntity

// تعریف دیتابیس Room
@Database(
    entities = [CurrentWeatherEntity::class, DailyForecastEntity::class], // لیست Entityها
    version = 1, // نسخه دیتابیس
    exportSchema = false // برای جلوگیری از تولید schema file
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao // ارائه DAO
}


