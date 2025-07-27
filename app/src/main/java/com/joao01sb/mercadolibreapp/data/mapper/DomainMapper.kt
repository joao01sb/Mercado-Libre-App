package com.joao01sb.mercadolibreapp.data.mapper

import com.joao01sb.mercadolibreapp.data.remote.response.SearchResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDetailResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDescriptionResponse
import com.joao01sb.mercadolibreapp.data.remote.response.CategoryResponse
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.ProductAttribute

fun SearchResponse.toDomain(): List<Product> {
    return resultProducts.map { item ->
        Product(
            id = item.id,
            title = item.title,
            price = item.price,
            currencyId = item.currencyId,
            thumbnail = item.thumbnail,
            condition = item.condition,
            availableQuantity = item.availableQuantity,
            permalink = item.permalink,
            originalPrice = item.originalPrice,
            freeShipping = item.shipping.freeShipping,
            rating = null,
            installmentsQuantity = item.installments?.quantity ?: 1,
            installmentsRate = item.installments?.rate ?: 0.0,
            sellerId = item.seller.id,
            categoryId = item.categoryId,
            isSponsored = item.listingTypeId.contains("gold") || item.listingTypeId.contains("premium"),
            reviewsCount = 0
        )
    }
}

fun ProductDetailResponse.toDomain(): ProductDetail {
    val brand = attributes.find { it.id == "BRAND" }?.valueName

    val model = attributes.find { it.id == "MODEL" }?.valueName

    val warranty = saleTerms.find { it.id == "WARRANTY_TIME" }?.valueName

    return ProductDetail(
        id = id,
        title = title,
        price = price,
        originalPrice = originalPrice,
        currencyId = currencyId,
        condition = condition,
        thumbnail = thumbnail,
        pictures = pictures.map { it.url },
        permalink = permalink,
        categoryId = categoryId,
        sellerId = sellerId,
        acceptsMercadoPago = acceptsMercadoPago,
        freeShipping = shipping.freeShipping,
        availableQuantity = initialQuantity,
        attributes = attributes.map { attr ->
            ProductAttribute(
                id = attr.id,
                name = attr.name,
                valueName = attr.valueName
            )
        },
        rating = null,
        reviewsCount = 0,
        warranty = warranty,
        brand = brand,
        model = model,
        installmentsQuantity = 1,
        installmentsRate = 0.0,
        tags = shipping.tags
    )
}

fun ProductDescriptionResponse.toDomain(): ProductDescription {
    return ProductDescription(
        text = text,
        plainText = plainText
    )
}

fun CategoryResponse.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        picture = picture,
        totalItems = totalItemsInThisCategory
    )
}
