package com.joao01sb.mercadolibreapp.data.remote.model.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultProduct(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("condition") val condition: String,
    @SerialName("thumbnail_id") val thumbnailId: String? = null,
    @SerialName("catalog_product_id") val catalogProductId: String? = null,
    @SerialName("listing_type_id") val listingTypeId: String,
    @SerialName("sanitized_title") val sanitizedTitle: String? = null,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("permalink") val permalink: String,
    @SerialName("buying_mode") val buyingMode: String,
    @SerialName("site_id") val siteId: String,
    @SerialName("category_id") val categoryId: String,
    @SerialName("domain_id") val domainId: String? = null,
    @SerialName("variation_id") val variationId: String? = null,
    @SerialName("currency_id") val currencyId: String,
    @SerialName("order_backend") val orderBackend: Int? = null,
    @SerialName("price") val price: Double,
    @SerialName("original_price") val originalPrice: Double? = null,
    @SerialName("sale_price") val salePrice: SalePrice? = null,
    @SerialName("available_quantity") val availableQuantity: Int,
    @SerialName("official_store_id") val officialStoreId: Long? = null,
    @SerialName("official_store_name") val officialStoreName: String? = null,
    @SerialName("use_thumbnail_id") val useThumbnailId: Boolean = false,
    @SerialName("accepts_mercadopago") val acceptsMercadoPago: Boolean,
    @SerialName("variation_filters") val variationFilters: List<String>? = null,
    @SerialName("shipping") val shipping: Shipping,
    @SerialName("stop_time") val stopTime: String? = null,
    @SerialName("seller") val seller: Seller,
    @SerialName("address") val address: Address,
    @SerialName("attributes") val attributes: List<ProductAttribute>,
    @SerialName("variations_data") val variationsData: Map<String, VariationData>? = null,
    @SerialName("installments") val installments: Installments? = null,
    @SerialName("winner_item_id") val winnerItemId: String? = null,
    @SerialName("catalog_listing") val catalogListing: Boolean = false,
    @SerialName("discounts") val discounts: String? = null,
    @SerialName("promotion_decorations") val promotionDecorations: String? = null,
    @SerialName("promotions") val promotions: String? = null,
    @SerialName("inventory_id") val inventoryId: String? = null,
    @SerialName("installments_motors") val installmentsMotors: String? = null,
    @SerialName("result_type") val resultType: String? = null
)