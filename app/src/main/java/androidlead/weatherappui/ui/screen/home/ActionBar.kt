package androidlead.weatherappui.ui.screen.home

import androidx.compose.foundation.lazy.items

import androidlead.weatherappui.R
import androidlead.weatherappui.ui.theme.ColorGradient1
import androidlead.weatherappui.ui.theme.ColorGradient2
import androidlead.weatherappui.ui.theme.ColorGradient3
import androidlead.weatherappui.ui.theme.ColorImageShadow
import androidlead.weatherappui.ui.theme.ColorSurface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Preview
@Composable
fun ActionBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

            ControlButton()

        Column(horizontalAlignment = Alignment.CenterHorizontally,) {
            Spacer(modifier = Modifier.padding(top = 44.dp))
            Row(horizontalArrangement =Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                Image(painterResource(id = R.drawable.baseline_location_on_24), contentDescription = "location",modifier= Modifier
                .size(38.dp))
                CitySelectorDialog()
            }

        }
        ProfileButton()


    }
}


@Preview
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



@Preview
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






@Preview
@Composable
fun CitySelectorDialog() {
    var dialogOpen by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("Select City") }
    val cities = listOf("Tehran", "Mashhad", "Shiraz", "Isfahan", "Bandar Abbas", "Madrid")

    // دکمه انتخاب شهر
    Row(
        modifier = Modifier
            .clickable { dialogOpen = true }
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.25f to ColorGradient2,
                    1f to ColorGradient3,
                ),
                shape = RoundedCornerShape(25.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = selectedCity, color = Color.White, modifier = Modifier.padding(end = 10.dp))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Select City",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }

    // دیالوگ سفارشی با پس‌زمینه گرادیانت
    if (dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(0.1f to ColorGradient3, 0.8f to ColorGradient2)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Select your city",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 8.dp)
                    )

                    LazyColumn(modifier = Modifier.height(290.dp)) {
                        items(cities) { city ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCity = city
                                        dialogOpen = false
                                    }
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedCity == city,
                                    onClick = {
                                        selectedCity = city
                                        dialogOpen = false
                                    }
                                )
                                Text(
                                    text = city,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

