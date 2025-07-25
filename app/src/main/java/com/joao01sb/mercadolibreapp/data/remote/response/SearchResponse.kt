package com.joao01sb.mercadolibreapp.data.remote.response

import com.joao01sb.mercadolibreapp.data.remote.model.product.ResultProduct
import com.joao01sb.mercadolibreapp.data.remote.model.search.Filter
import com.joao01sb.mercadolibreapp.data.remote.model.search.Paging
import com.joao01sb.mercadolibreapp.data.remote.model.search.PdpTracking
import com.joao01sb.mercadolibreapp.data.remote.model.search.Sort
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("site_id") val siteId: String,
    @SerialName("country_default_time_zone") val countryDefaultTimeZone: String,
    @SerialName("query") val query: String,
    @SerialName("paging") val paging: Paging,
    @SerialName("results") val resultProducts: List<ResultProduct>,
    @SerialName("sort") val sort: Sort,
    @SerialName("available_sorts") val availableSorts: List<Sort>,
    @SerialName("filters") val filters: List<Filter>,
    @SerialName("available_filters") val availableFilters: List<Filter>,
    @SerialName("pdp_tracking") val pdpTracking: PdpTracking? = null,
    @SerialName("user_context") val userContext: String? = null,
    @SerialName("ranking_introspection") val rankingIntrospection: Map<String, String> = emptyMap()
)
