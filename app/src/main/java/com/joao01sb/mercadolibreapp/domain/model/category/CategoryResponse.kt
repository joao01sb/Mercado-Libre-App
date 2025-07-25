package com.joao01sb.mercadolibreapp.domain.model.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("picture") val picture: String,
    @SerialName("permalink") val permalink: String? = null,
    @SerialName("total_items_in_this_category") val totalItemsInThisCategory: Int,
    @SerialName("path_from_root") val pathFromRoot: List<CategoryPath>,
    @SerialName("children_categories") val childrenCategories: List<String> = emptyList(),
    @SerialName("attribute_types") val attributeTypes: String,
    @SerialName("settings") val settings: CategorySettings,
    @SerialName("channels_settings") val channelsSettings: List<ChannelSettings> = emptyList(),
    @SerialName("meta_categ_id") val metaCategId: String? = null,
    @SerialName("attributable") val attributable: Boolean,
    @SerialName("date_created") val dateCreated: String
)
