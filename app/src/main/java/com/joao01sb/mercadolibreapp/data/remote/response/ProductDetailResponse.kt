package com.joao01sb.mercadolibreapp.data.remote.response

import com.joao01sb.mercadolibreapp.data.remote.model.detail.ProductDetailAttribute
import com.joao01sb.mercadolibreapp.data.remote.model.detail.ProductDetailShipping
import com.joao01sb.mercadolibreapp.data.remote.model.detail.ProductPicture
import com.joao01sb.mercadolibreapp.data.remote.model.detail.SaleTerm
import com.joao01sb.mercadolibreapp.data.remote.model.detail.SellerAddress
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponse(
    @SerialName("id") val id: String,
    @SerialName("site_id") val siteId: String,
    @SerialName("title") val title: String,
    @SerialName("seller_id") val sellerId: Long,
    @SerialName("category_id") val categoryId: String,
    @SerialName("official_store_id") val officialStoreId: Long? = null,
    @SerialName("price") val price: Double,
    @SerialName("base_price") val basePrice: Double,
    @SerialName("original_price") val originalPrice: Double? = null,
    @SerialName("currency_id") val currencyId: String,
    @SerialName("initial_quantity") val initialQuantity: Int,
    @SerialName("sale_terms") val saleTerms: List<SaleTerm>,
    @SerialName("buying_mode") val buyingMode: String,
    @SerialName("listing_type_id") val listingTypeId: String,
    @SerialName("condition") val condition: String,
    @SerialName("permalink") val permalink: String,
    @SerialName("thumbnail_id") val thumbnailId: String,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("pictures") val pictures: List<ProductPicture>,
    @SerialName("video_id") val videoId: String? = null,
    @SerialName("descriptions") val descriptions: List<String> = emptyList(),
    @SerialName("accepts_mercadopago") val acceptsMercadoPago: Boolean,
    @SerialName("non_mercado_pago_payment_methods") val nonMercadoPagoPaymentMethods: List<String> = emptyList(),
    @SerialName("shipping") val shipping: ProductDetailShipping,
    @SerialName("international_delivery_mode") val internationalDeliveryMode: String,
    @SerialName("seller_address") val sellerAddress: SellerAddress,
    @SerialName("seller_contact") val sellerContact: String? = null,
    @SerialName("location") val location: Map<String, String> = emptyMap(),
    @SerialName("coverage_areas") val coverageAreas: List<String> = emptyList(),
    @SerialName("attributes") val attributes: List<ProductDetailAttribute>
)