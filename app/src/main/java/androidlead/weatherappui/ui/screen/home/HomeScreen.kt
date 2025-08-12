package androidlead.weatherappui.ui.screen.home

import androidlead.weatherappui.R
import androidlead.weatherappui.ui.theme.ColorBackground
import androidlead.weatherappui.ui.theme.ColorImageShadow
import androidlead.weatherappui.ui.theme.ColorSurface
import androidlead.weatherappui.ui.theme.ColorTextAction
import androidlead.weatherappui.ui.theme.ColorTextPrimary
import androidlead.weatherappui.ui.theme.ColorTextPrimaryVariant
import androidlead.weatherappui.ui.theme.ColorTextSecondary
import androidlead.weatherappui.ui.theme.ColorTextSecondaryVariant
import androidlead.weatherappui.ui.theme.ColorWindForecast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidlead.weatherappui.ui.screen.components.CitySelector
import androidlead.weatherappui.ui.domain.model.CurrentWeather
import androidlead.weatherappui.ui.domain.model.DailyForecast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Stable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp


@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddings)
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                item {
                    ActionBar(
                        onCitySelected = { viewModel.loadWeatherData(it) },
                        currentCity = uiState.currentWeather?.cityName ?: "Select City"
                    )
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                uiState.currentWeather?.let {
                    item { DailyForecastCard(currentWeather = it) }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                uiState.currentWeather?.let {
                    item { AirQualityCard(airQualityData = it.getAirQualityItems()) }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                uiState.dailyForecasts.let {
                    item { WeeklyForecast(forecasts = it) }
                }
            }
        }
    }
}

// Data class to represent AirQuality items for the UI
data class AirQualityItem(val title: String, val value: String, val icon: Int)

// Extension function to map CurrentWeather to a list of AirQualityItems
fun CurrentWeather.getAirQualityItems(): List<AirQualityItem> {
    return listOf(
        AirQualityItem("O₃", "${o3?.toInt() ?: "N/A"}", R.drawable.ic_o3),
        AirQualityItem("SO₂", "${so2?.toInt() ?: "N/A"}", R.drawable.ic_so2),
        AirQualityItem("CO", "${co?.toInt() ?: "N/A"}", R.drawable.ic_co),
        AirQualityItem("Humidity", "$humidity%", R.drawable.ic_humidity)
    )
}

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    currentCity: String,
    onCitySelected: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ControlButton()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(top = 44.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.baseline_location_on_24),
                    contentDescription = "location",
                    modifier = Modifier.size(38.dp)
                )
                CitySelectorDialog(currentCity = currentCity, onCitySelected = onCitySelected)
            }
        }
        ProfileButton()
    }
}

@Composable
private fun ControlButton(
    modifier: Modifier = Modifier
) {
    Surface(
        color = ColorSurface,
        shape = CircleShape,
        modifier = modifier
            .size(48.dp)
            .customShadow(
                color = Color.Black,
                alpha = 0.15f,
                shadowRadius = 16.dp,
                borderRadius = 48.dp,
                offsetY = 4.dp
            ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_control),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ProfileButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 1.5.dp,
                color = ColorSurface,
                shape = CircleShape
            )
            .customShadow(
                color = ColorImageShadow,
                alpha = 0.7f,
                shadowRadius = 12.dp,
                borderRadius = 48.dp,
                offsetY = 6.dp
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_profile),
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Composable
fun CitySelectorDialog(
    currentCity: String,
    onCitySelected: (String) -> Unit
) {
    var dialogOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clickable { dialogOpen = true }
            .background(
                brush = Brush.linearGradient(
                    0f to Color(0xFF6E8EFC),
                    0.25f to Color(0xFF816DFF),
                    1f to Color(0xFFB13DFF),
                ),
                shape = RoundedCornerShape(25.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currentCity, color = Color.White, modifier = Modifier.padding(end = 10.dp))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Select City",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                CitySelector(onCitySelected = {
                    onCitySelected(it)
                    dialogOpen = false
                })
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AirQualityCard(
    modifier: Modifier = Modifier,
    airQualityData: List<AirQualityItem>
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = ColorSurface
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 18.dp,
                horizontal = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AirQualityHeader()
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                airQualityData.onEach { item ->
                    AirQualityInfo(
                        data = item,
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
            }
        }
    }
}
@Composable
private fun AirQualityHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_air_quality_header),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF6E8EFC)
            )
            Text(
                text = "Air Quality",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp
                )
            )
        }
        RefreshButton()
    }
}

@Composable
private fun RefreshButton(
    modifier: Modifier = Modifier
) {
    Surface(
        color = ColorSurface,
        shape = CircleShape,
        modifier = modifier
            .size(32.dp)
            .customShadow(
                color = Color.Black,
                alpha = 0.15f,
                shadowRadius = 16.dp,
                borderRadius = 32.dp,
                offsetY = 4.dp
            ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
@Composable
private fun AirQualityInfo(
    modifier: Modifier = Modifier,
    data: AirQualityItem
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            painter = painterResource(data.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF6E8EFC)
        )
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.labelSmall,
                color = ColorTextPrimaryVariant
            )
            Text(
                text = data.value,
                style = MaterialTheme.typography.labelSmall,
                color = ColorTextPrimary
            )
        }
    }
}

@Composable
fun DailyForecastCard(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (forecastImage, forecastValue, background) = createRefs()
        CardBackground(
            modifier = Modifier.constrainAs(background) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = parent.bottom
                )
                height = Dimension.fillToConstraints
            }
        )
        Image(
            painter = painterResource(id = R.drawable.img_sub_rain),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(175.dp)
                .constrainAs(forecastImage) {
                    start.linkTo(anchor = parent.start, margin = 4.dp)
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = currentWeather.description,
            style = MaterialTheme.typography.titleLarge,
            color = ColorTextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(createRef()) {
                start.linkTo(anchor = parent.start, margin = 24.dp)
                top.linkTo(anchor = forecastImage.bottom)
            }
        )
        Text(
            text = currentWeather.lastUpdated,
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant,
            modifier = Modifier
                .constrainAs(createRef()) {
                    start.linkTo(anchor = createRef().start)
                    top.linkTo(anchor = createRef().bottom)
                }
                .padding(bottom = 24.dp)
        )
        ForecastValue(
            degree = currentWeather.temperature.toInt().toString(),
            description = "Feels like ${currentWeather.feelslike_c.toInt()}°",
            modifier = Modifier.constrainAs(forecastValue) {
                end.linkTo(anchor = parent.end, margin = 24.dp)
                top.linkTo(forecastImage.top)
                bottom.linkTo(forecastImage.bottom)
            }
        )
    }
}
@Composable
private fun CardBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    0f to Color(0xFF6E8EFC),
                    0.5f to Color(0xFF816DFF),
                    1f to Color(0xFFB13DFF)
                ),
                shape = RoundedCornerShape(32.dp)
            )
    )
}
@Composable
private fun ForecastValue(
    modifier: Modifier = Modifier,
    degree: String,
    description: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = degree,
                letterSpacing = 0.sp,
                style = TextStyle(
                    brush = Brush.verticalGradient(
                        0f to Color.White,
                        1f to Color.White.copy(alpha = 0.3f)
                    ),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "°",
                style = TextStyle(
                    brush = Brush.verticalGradient(
                        0f to Color.White,
                        1f to Color.White.copy(alpha = 0.3f)
                    ),
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Light,
                ),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant
        )
    }
}

@Composable
fun WeeklyForecast(
    modifier: Modifier = Modifier,
    forecasts: List<DailyForecast>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WeatherForecastHeader()
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = forecasts,
                key = { it.date }
            ) { item ->
                DailyForecastItem(
                    forecast = item
                )
            }
        }
    }
}
@Composable
private fun WeatherForecastHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Weekly forecast",
            style = MaterialTheme.typography.titleLarge,
            color = ColorTextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        ActionText()
    }
}
@Composable
private fun ActionText(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "Next month",
            style = MaterialTheme.typography.titleSmall,
            color = ColorTextAction,
            fontWeight = FontWeight.Medium
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = ColorTextAction
        )
    }
}
@Composable
private fun DailyForecastItem(
    modifier: Modifier = Modifier,
    forecast: DailyForecast
) {
    val updatedModifier = remember { modifier }
    val primaryTextColor = remember { ColorTextPrimary }
    val secondaryTextColor = remember { ColorTextPrimaryVariant }
    val temperatureTextStyle = remember {
        TextStyle(
            color = ColorTextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )
    }

    Column(
        modifier = updatedModifier
            .width(65.dp)
            .padding(
                horizontal = 10.dp,
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = forecast.dayOfWeek,
            style = MaterialTheme.typography.labelLarge,
            color = primaryTextColor
        )
        Text(
            text = forecast.date,
            style = MaterialTheme.typography.labelMedium,
            color = secondaryTextColor,
            fontWeight = FontWeight.Normal
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        WeatherImage(
            imageUrl = forecast.iconUrl
        )
        Spacer(
            modifier = Modifier.height(6.dp)
        )
        Text(
            text = "${forecast.avgTemp.toInt()}°",
            letterSpacing = 0.sp,
            style = temperatureTextStyle,
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        AirQualityIndicator(
            value = "${forecast.pm25.toInt()}",
            color = forecast.airQualityIndicatorColorHex
        )
    }
}

@Composable
private fun WeatherImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Icon", modifier = Modifier.size(60.dp))
    }
}

@Composable
private fun AirQualityIndicator(
    modifier: Modifier = Modifier,
    value: String,
    color: String
) {
    Surface(
        modifier = modifier,
        color = Color.fromHex(color),
        contentColor = ColorTextSecondary,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(35.dp)
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
fun Color.Companion.fromHex(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: IllegalArgumentException) {
        Color.Black
    }
}

@Stable
fun Modifier.customShadow(
    color: Color = Color.Black,
    alpha: Float = 0.5f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha = 0f).toArgb()

    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparent

        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}
