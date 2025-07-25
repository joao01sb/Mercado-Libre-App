package com.joao01sb.mercadolibreapp.data.remote.model.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalePrice(
    @SerialName("price_id") val priceId: String? = null,
    @SerialName("amount") val amount: Double,
    @SerialName("conditions") val conditions: SalePriceConditions? = null,
    @SerialName("currency_id") val currencyId: String,
    @SerialName("exchange_rate") val exchangeRate: Double? = null,
    @SerialName("payment_method_prices") val paymentMethodPrices: List<String> = emptyList(),
    @SerialName("payment_method_type") val paymentMethodType: String? = null,
    @SerialName("regular_amount") val regularAmount: Double,
    @SerialName("type") val type: String,
    @SerialName("metadata") val metadata: SalePriceMetadata? = null
)

@Serializable
data class SalePriceConditions(
    @SerialName("eligible") val eligible: Boolean,
    @SerialName("context_restrictions") val contextRestrictions: List<String> = emptyList(),
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time") val endTime: String? = null
)

@Serializable
data class SalePriceMetadata(
    @SerialName("promotion_offer_type") val promotionOfferType: String? = null,
    @SerialName("promotion_offer_sub_type") val promotionOfferSubType: String? = null,
    @SerialName("promotion_id") val promotionId: String? = null,
    @SerialName("promotion_type") val promotionType: String? = null
)

@Serializable
data class Shipping(
    @SerialName("store_pick_up") val storePickUp: Boolean = false,
    @SerialName("free_shipping") val freeShipping: Boolean,
    @SerialName("logistic_type") val logisticType: String? = null,
    @SerialName("mode") val mode: String? = null,
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("benefits") val benefits: String? = null,
    @SerialName("promise") val promise: String? = null,
    @SerialName("shipping_score") val shippingScore: Int? = null
)

@Serializable
data class Seller(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String
)

@Serializable
data class Address(
    @SerialName("state_id") val stateId: String? = null,
    @SerialName("state_name") val stateName: String,
    @SerialName("city_id") val cityId: String? = null,
    @SerialName("city_name") val cityName: String? = null
)

@Serializable
data class Installments(
    @SerialName("quantity") val quantity: Int,
    @SerialName("amount") val amount: Double,
    @SerialName("rate") val rate: Double,
    @SerialName("currency_id") val currencyId: String,
    @SerialName("metadata") val metadata: InstallmentsMetadata? = null
)

@Serializable
data class InstallmentsMetadata(
    @SerialName("meliplus_installments") val meliplusInstallments: Boolean = false,
    @SerialName("additional_bank_interest") val additionalBankInterest: Boolean = false
)
