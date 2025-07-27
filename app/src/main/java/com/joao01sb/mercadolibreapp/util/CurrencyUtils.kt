package com.joao01sb.mercadolibreapp.util

import android.content.Context
import com.joao01sb.mercadolibreapp.R
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object CurrencyUtils {

    fun formatPrice(context: Context, amount: Double, currencyId: String): String {
        val locale = getLocaleForCurrency(currencyId)
        val numberFormat = NumberFormat.getCurrencyInstance(locale)

        when (currencyId.uppercase()) {
            "ARS" -> {
                numberFormat.currency = Currency.getInstance("ARS")
                return numberFormat.format(amount)
            }
            "USD" -> {
                numberFormat.currency = Currency.getInstance("USD")
                return numberFormat.format(amount)
            }
            "BRL" -> {
                numberFormat.currency = Currency.getInstance("BRL")
                return numberFormat.format(amount)
            }
            else -> {
                return context.getString(R.string.price_format, formatNumber(amount))
            }
        }
    }

    fun formatDiscount(context: Context, discount: Int): String {
        return context.getString(R.string.discount_format, discount)
    }

    fun formatInstallments(
        context: Context,
        quantity: Int,
        installmentValue: Double,
        currencyId: String
    ): String {
        val formattedValue = formatInstallmentValue(installmentValue, currencyId)
        return context.getString(R.string.installments_format, quantity, formattedValue)
    }

    fun calculateDiscountPercentage(currentPrice: Double, originalPrice: Double): Int {
        return if (originalPrice > 0) {
            ((originalPrice - currentPrice) / originalPrice * 100).toInt()
        } else 0
    }


    fun hasDiscount(currentPrice: Double, originalPrice: Double?): Boolean {
        return originalPrice != null && originalPrice > currentPrice
    }


    private fun getLocaleForCurrency(currencyId: String): Locale {
        return when (currencyId.uppercase()) {
            "ARS" -> Locale("es", "AR")
            "USD" -> Locale.US
            "BRL" -> Locale("pt", "BR")
            else -> Locale.getDefault()
        }
    }


    private fun formatNumber(amount: Double): String {
        return String.format("%.0f", amount)
    }

    private fun formatInstallmentValue(amount: Double, currencyId: String): String {
        return when (currencyId.uppercase()) {
            "ARS" -> "$${formatNumber(amount)}"
            "USD" -> "$${String.format("%.2f", amount)}"
            "BRL" -> "R$ ${String.format("%.2f", amount)}"
            else -> formatNumber(amount)
        }
    }


    fun getCurrencySymbol(currencyId: String): String {
        return when (currencyId.uppercase()) {
            "ARS" -> "$"
            "USD" -> "$"
            "BRL" -> "R$"
            else -> "$"
        }
    }
}