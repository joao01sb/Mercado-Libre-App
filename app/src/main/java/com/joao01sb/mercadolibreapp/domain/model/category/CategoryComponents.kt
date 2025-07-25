package com.joao01sb.mercadolibreapp.domain.model.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryPath(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

@Serializable
data class CategorySettings(
    @SerialName("adult_content") val adultContent: Boolean,
    @SerialName("buying_allowed") val buyingAllowed: Boolean,
    @SerialName("buying_modes") val buyingModes: List<String>,
    @SerialName("catalog_domain") val catalogDomain: String? = null,
    @SerialName("coverage_areas") val coverageAreas: String,
    @SerialName("currencies") val currencies: List<String>,
    @SerialName("fragile") val fragile: Boolean,
    @SerialName("immediate_payment") val immediatePayment: String,
    @SerialName("item_conditions") val itemConditions: List<String>,
    @SerialName("items_reviews_allowed") val itemsReviewsAllowed: Boolean,
    @SerialName("listing_allowed") val listingAllowed: Boolean,
    @SerialName("max_description_length") val maxDescriptionLength: Int,
    @SerialName("max_pictures_per_item") val maxPicturesPerItem: Int,
    @SerialName("max_pictures_per_item_var") val maxPicturesPerItemVar: Int,
    @SerialName("max_sub_title_length") val maxSubTitleLength: Int,
    @SerialName("max_title_length") val maxTitleLength: Int,
    @SerialName("max_variations_allowed") val maxVariationsAllowed: Int,
    @SerialName("maximum_price") val maximumPrice: Double? = null,
    @SerialName("maximum_price_currency") val maximumPriceCurrency: String,
    @SerialName("minimum_price") val minimumPrice: Double,
    @SerialName("minimum_price_currency") val minimumPriceCurrency: String,
    @SerialName("mirror_category") val mirrorCategory: String? = null,
    @SerialName("mirror_master_category") val mirrorMasterCategory: String? = null,
    @SerialName("mirror_slave_categories") val mirrorSlaveCategories: List<String> = emptyList(),
    @SerialName("price") val price: String,
    @SerialName("reservation_allowed") val reservationAllowed: String,
    @SerialName("restrictions") val restrictions: List<String> = emptyList(),
    @SerialName("rounded_address") val roundedAddress: Boolean,
    @SerialName("seller_contact") val sellerContact: String,
    @SerialName("shipping_options") val shippingOptions: List<String>,
    @SerialName("shipping_profile") val shippingProfile: String,
    @SerialName("show_contact_information") val showContactInformation: Boolean,
    @SerialName("simple_shipping") val simpleShipping: String,
    @SerialName("stock") val stock: String,
    @SerialName("sub_vertical") val subVertical: String? = null,
    @SerialName("subscribable") val subscribable: Boolean,
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("vertical") val vertical: String? = null,
    @SerialName("vip_subdomain") val vipSubdomain: String,
    @SerialName("buyer_protection_programs") val buyerProtectionPrograms: List<String> = emptyList(),
    @SerialName("status") val status: String
)

@Serializable
data class ChannelSettings(
    @SerialName("channel") val channel: String,
    @SerialName("settings") val settings: ChannelSettingsDetail
)

@Serializable
data class ChannelSettingsDetail(
    @SerialName("minimum_price") val minimumPrice: Double? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("buying_modes") val buyingModes: List<String>? = null,
    @SerialName("immediate_payment") val immediatePayment: String? = null
)
