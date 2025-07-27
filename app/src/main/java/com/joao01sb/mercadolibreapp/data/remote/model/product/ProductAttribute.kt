package com.joao01sb.mercadolibreapp.data.remote.model.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductAttribute(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("value_name") val valueName: String? = null,
    @SerialName("value_id") val valueId: String? = null,
    @SerialName("attribute_group_id") val attributeGroupId: String? = null,
    @SerialName("attribute_group_name") val attributeGroupName: String? = null,
    @SerialName("value_struct") val valueStruct: ValueStruct? = null,
    @SerialName("values") val values: List<AttributeValue>? = null,
    @SerialName("source") val source: Long? = null,
    @SerialName("value_type") val valueType: String? = null
)

@Serializable
data class ValueStruct(
    @SerialName("number") val number: Double? = null,
    @SerialName("unit") val unit: String? = null
)

@Serializable
data class AttributeValue(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("struct") val struct: ValueStruct? = null,
    @SerialName("source") val source: Long? = null
)
