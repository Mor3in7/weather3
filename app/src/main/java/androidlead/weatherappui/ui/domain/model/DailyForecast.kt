package androidlead.weatherappui.ui.domain.model

data class DailyForecast(
    val city: String,
    val dayOfWeek: String, // روز هفته (مثلاً "Mon")
    val date: String, // تاریخ (مثلاً "13 Feb")
    val description: String,
    val iconUrl: String,
    val maxTemp: Float, // حداکثر دما
    val minTemp: Float, // حداقل دما
    val dailyChanceOfRain: Float, // احتمال بارش روزانه (درصد)
    val pm25: Float, //QI
    val airQualityIndicatorColorHex: String,
)
