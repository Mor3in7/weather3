package androidlead.weatherappui.ui.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitySearchItemDto(
    val name: String,
    val region: String? = null,
    val country: String? = null
)
