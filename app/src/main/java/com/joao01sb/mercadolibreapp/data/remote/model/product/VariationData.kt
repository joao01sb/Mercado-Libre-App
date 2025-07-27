package com.joao01sb.mercadolibreapp.data.remote.model.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VariationData(
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("ratio") val ratio: String,
    @SerialName("name") val name: String,
    @SerialName("pictures_qty") val picturesQty: Int,
    @SerialName("price") val price: Double,
    @SerialName("inventory_id") val inventoryId: String? = null,
    @SerialName("user_product_id") val userProductId: String,
    @SerialName("attributes") val attributes: List<ProductAttribute> = emptyList(),
    @SerialName("attribute_combinations") val attributeCombinations: List<AttributeCombination>
)

@Serializable
data class AttributeCombination(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("value_id") val valueId: String? = null,
    @SerialName("value_name") val valueName: String,
    @SerialName("value_struct") val valueStruct: ValueStruct? = null,
    @SerialName("values") val values: List<AttributeValue>? = null
)
