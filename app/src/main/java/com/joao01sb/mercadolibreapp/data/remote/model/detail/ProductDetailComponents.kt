package com.joao01sb.mercadolibreapp.data.remote.model.detail

import com.joao01sb.mercadolibreapp.data.remote.model.product.AttributeValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleTerm(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("value_id") val valueId: String? = null,
    @SerialName("value_name") val valueName: String? = null,
    @SerialName("value_struct") val valueStruct: SaleTermStruct? = null,
    @SerialName("values") val values: List<SaleTermValue>,
    @SerialName("value_type") val valueType: String
)

@Serializable
data class SaleTermStruct(
    @SerialName("number") val number: Int,
    @SerialName("unit") val unit: String
)

@Serializable
data class SaleTermValue(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String,
    @SerialName("struct") val struct: SaleTermStruct? = null
)

@Serializable
data class ProductPicture(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("secure_url") val secureUrl: String,
    @SerialName("size") val size: String,
    @SerialName("max_size") val maxSize: String,
    @SerialName("quality") val quality: String
)

@Serializable
data class ProductDetailShipping(
    @SerialName("mode") val mode: String,
    @SerialName("methods") val methods: List<String> = emptyList(),
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("dimensions") val dimensions: String? = null,
    @SerialName("local_pick_up") val localPickUp: Boolean,
    @SerialName("free_shipping") val freeShipping: Boolean,
    @SerialName("logistic_type") val logisticType: String,
    @SerialName("store_pick_up") val storePickUp: Boolean
)

@Serializable
data class SellerAddress(
    @SerialName("city") val city: AddressCity,
    @SerialName("state") val state: AddressState,
    @SerialName("country") val country: AddressCountry,
    @SerialName("search_location") val searchLocation: SearchLocation,
    @SerialName("id") val id: Long
)

@Serializable
data class AddressCity(
    @SerialName("name") val name: String
)

@Serializable
data class AddressState(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
data class AddressCountry(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
data class SearchLocation(
    @SerialName("neighborhood") val neighborhood: LocationItem,
    @SerialName("city") val city: LocationItem,
    @SerialName("state") val state: LocationItem
)

@Serializable
data class LocationItem(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
data class ProductDetailAttribute(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("value_id") val valueId: String? = null,
    @SerialName("value_name") val valueName: String? = null,
    @SerialName("values") val values: List<AttributeValue>,
    @SerialName("value_type") val valueType: String
)
