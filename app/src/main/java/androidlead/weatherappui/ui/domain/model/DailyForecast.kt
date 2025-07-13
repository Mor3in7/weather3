package androidlead.weatherappui.ui.domain.model

data class DailyForecast(
    val dateEpochMillis: Long, // تاریخ به میلی‌ثانیه
    val dayOfWeek: String, // روز هفته (مثلاً "Mon")
    val date: String, // تاریخ (مثلاً "13 Feb")
    val temperature: String, // دما (با "°" مثلاً "21°")
    val description: String,
    val iconUrl: String,
    val maxTemp: Float, // حداکثر دما
    val minTemp: Float, // حداقل دما
    val dailyChanceOfRain: Float, // احتمال بارش روزانه (درصد)
    val airQuality: String, // برای سادگی اینجا از dailyChanceOfRain استفاده می‌کنیم و آن را به درصد تبدیل می‌کنیم
    val airQualityIndicatorColorHex: String, // رنگ شاخص کیفیت هوا
    val timestamp: Long // زمان دریافت داده
)
