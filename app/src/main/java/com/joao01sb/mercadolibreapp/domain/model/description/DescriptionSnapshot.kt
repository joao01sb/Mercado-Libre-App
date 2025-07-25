package com.joao01sb.mercadolibreapp.domain.model.description

@Serializable
data class DescriptionSnapshot(
    @SerialName("url") val url: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("status") val status: String
)