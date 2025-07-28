import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.ProductAttribute
import com.joao01sb.mercadolibreapp.presentation.ui.state.ProductDetailUiData

object ProductDataTest {
    val mockProducts = listOf(
        Product(
            id = "MLA1",
            title = "iPhone 13 Pro Max",
            price = 899990.0,
            currencyId = "ARS",
            thumbnail = "https://example.com/iphone.jpg",
            condition = "new",
            availableQuantity = 10,
            permalink = "https://example.com/iphone",
            originalPrice = 999990.0,
            freeShipping = true,
            rating = 4.8,
            installmentsQuantity = 6,
            installmentsRate = 0.0,
            sellerId = 123456,
            categoryId = "MLA1055",
            isSponsored = false,
            reviewsCount = 1250
        ),
        Product(
            id = "MLA2",
            title = "Samsung Galaxy S23",
            price = 699990.0,
            currencyId = "ARS",
            thumbnail = "https://example.com/samsung.jpg",
            condition = "new",
            availableQuantity = 15,
            permalink = "https://example.com/samsung",
            originalPrice = 799990.0,
            freeShipping = true,
            rating = 4.5,
            installmentsQuantity = 12,
            installmentsRate = 0.0,
            sellerId = 789012,
            categoryId = "MLA1055",
            isSponsored = false,
            reviewsCount = 980
        ),
        Product(
            id = "MLA3",
            title = "Xiaomi Redmi Note 12",
            price = 299990.0,
            currencyId = "ARS",
            thumbnail = "https://example.com/xiaomi.jpg",
            condition = "new",
            availableQuantity = 25,
            permalink = "https://example.com/xiaomi",
            originalPrice = null,
            freeShipping = true,
            rating = 4.3,
            installmentsQuantity = 6,
            installmentsRate = 0.0,
            sellerId = 345678,
            categoryId = "MLA1055",
            isSponsored = false,
            reviewsCount = 756
        ),
        Product(
            id = "MLA4",
            title = "Motorola Moto G52",
            price = 199990.0,
            currencyId = "ARS",
            thumbnail = "https://example.com/motorola.jpg",
            condition = "new",
            availableQuantity = 30,
            permalink = "https://example.com/motorola",
            originalPrice = 249990.0,
            freeShipping = true,
            rating = 4.1,
            installmentsQuantity = 6,
            installmentsRate = 0.0,
            sellerId = 901234,
            categoryId = "MLA1055",
            isSponsored = false,
            reviewsCount = 432
        ),
        Product(
            id = "MLA5",
            title = "OnePlus Nord CE 3",
            price = 549990.0,
            currencyId = "ARS",
            thumbnail = "https://example.com/oneplus.jpg",
            condition = "new",
            availableQuantity = 8,
            permalink = "https://example.com/oneplus",
            originalPrice = 599990.0,
            freeShipping = true,
            rating = 4.6,
            installmentsQuantity = 12,
            installmentsRate = 0.0,
            sellerId = 567890,
            categoryId = "MLA1055",
            isSponsored = false,
            reviewsCount = 623
        )
    )

    val mockProductDetail = ProductDetail(
        id = mockProducts.first().id,
        title = mockProducts.first().title,
        price = mockProducts.first().price,
        originalPrice = mockProducts.first().originalPrice,
        currencyId = mockProducts.first().currencyId,
        condition = mockProducts.first().condition,
        thumbnail = mockProducts.first().thumbnail,
        pictures = listOf(
            mockProducts.first().thumbnail,
            "https://example.com/iphone2.jpg",
            "https://example.com/iphone3.jpg"
        ),
        permalink = mockProducts.first().permalink,
        categoryId = mockProducts.first().categoryId ?: "MLA1055",
        sellerId = mockProducts.first().sellerId ?: 123456789,
        acceptsMercadoPago = true,
        freeShipping = mockProducts.first().freeShipping,
        availableQuantity = mockProducts.first().availableQuantity,
        attributes = listOf(
            ProductAttribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            ProductAttribute(id = "MODEL", name = "Modelo", valueName = "13 Pro Max")
        ),
        rating = mockProducts.first().rating,
        reviewsCount = mockProducts.first().reviewsCount,
        warranty = "Garantía de fábrica: 12 meses",
        brand = "Apple",
        model = "13 Pro Max",
        installmentsQuantity = mockProducts.first().installmentsQuantity,
        installmentsRate = mockProducts.first().installmentsRate,
        tags = listOf("good_quality_picture", "immediate_payment")
    )

    val mockCategory = Category(
        id = "MLA1055",
        name = "Celulares y Smartphones",
        picture = "https://example.com/category.png",
        totalItems = 25000
    )

    val mockProductDetailUiDataWithDescription = ProductDetailUiData(
        baseProduct = mockProducts.first(),
        productDetail = mockProductDetail,
        description = "Esta é uma descrição detalhada do produto ${mockProducts.first().title} com todas as especificações técnicas.",
        category = mockCategory,
        query = "iphone"
    )

    val mockProductDetailUiDataWithoutDescription = ProductDetailUiData(
        baseProduct = mockProducts.first(),
        productDetail = mockProductDetail,
        description = null,
        category = mockCategory,
        query = "iphone"
    )
}
