package com.joao01sb.mercadolibreapp.data.remote.response

import com.joao01sb.mercadolibreapp.data.remote.model.description.DescriptionSnapshot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDescriptionResponse(
    @SerialName("text") val text: String,
    @SerialName("plain_text") val plainText: String,
    @SerialName("last_updated") val lastUpdated: String,
    @SerialName("date_created") val dateCreated: String,
    @SerialName("snapshot") val snapshot: DescriptionSnapshot
)