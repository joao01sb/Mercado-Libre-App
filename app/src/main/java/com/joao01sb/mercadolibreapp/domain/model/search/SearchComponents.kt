package com.joao01sb.mercadolibreapp.domain.model.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    @SerialName("total") val total: Int,
    @SerialName("primary_results") val primaryResults: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("limit") val limit: Int
)

@Serializable
data class Sort(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
data class Filter(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("values") val values: List<FilterValue>
)

@Serializable
data class FilterValue(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("results") val results: Int? = null,
    @SerialName("path_from_root") val pathFromRoot: List<PathFromRoot>? = null
)

@Serializable
data class PathFromRoot(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
data class PdpTracking(
    @SerialName("group") val group: Boolean,
    @SerialName("product_info") val productInfo: List<ProductInfo>
)

@Serializable
data class ProductInfo(
    @SerialName("id") val id: String,
    @SerialName("score") val score: Int,
    @SerialName("status") val status: String
)
