package com.joao01sb.mercadolibreapp.data.mock

import com.joao01sb.mercadolibreapp.data.remote.model.category.CategoryPath
import com.joao01sb.mercadolibreapp.data.remote.model.category.CategorySettings
import com.joao01sb.mercadolibreapp.data.remote.model.category.ChannelSettings
import com.joao01sb.mercadolibreapp.data.remote.model.category.ChannelSettingsDetail
import com.joao01sb.mercadolibreapp.data.remote.model.description.DescriptionSnapshot
import com.joao01sb.mercadolibreapp.data.remote.model.detail.AddressCity
import com.joao01sb.mercadolibreapp.data.remote.model.detail.AddressCountry
import com.joao01sb.mercadolibreapp.data.remote.model.detail.AddressState
import com.joao01sb.mercadolibreapp.data.remote.model.detail.LocationItem
import com.joao01sb.mercadolibreapp.data.remote.model.detail.ProductDetailAttribute
import com.joao01sb.mercadolibreapp.data.remote.model.detail.ProductDetailShipping
import com.joao01sb.mercadolibreapp.data.remote.model.detail.ProductPicture
import com.joao01sb.mercadolibreapp.data.remote.model.detail.SaleTerm
import com.joao01sb.mercadolibreapp.data.remote.model.detail.SaleTermValue
import com.joao01sb.mercadolibreapp.data.remote.model.detail.SearchLocation
import com.joao01sb.mercadolibreapp.data.remote.model.detail.SellerAddress
import com.joao01sb.mercadolibreapp.data.remote.model.product.Address
import com.joao01sb.mercadolibreapp.data.remote.model.product.AttributeCombination
import com.joao01sb.mercadolibreapp.data.remote.model.product.AttributeValue
import com.joao01sb.mercadolibreapp.data.remote.model.product.Installments
import com.joao01sb.mercadolibreapp.data.remote.model.product.InstallmentsMetadata
import com.joao01sb.mercadolibreapp.data.remote.model.product.ProductAttribute
import com.joao01sb.mercadolibreapp.data.remote.model.product.ResultProduct
import com.joao01sb.mercadolibreapp.data.remote.model.product.SalePrice
import com.joao01sb.mercadolibreapp.data.remote.model.product.SalePriceConditions
import com.joao01sb.mercadolibreapp.data.remote.model.product.SalePriceMetadata
import com.joao01sb.mercadolibreapp.data.remote.model.product.Seller
import com.joao01sb.mercadolibreapp.data.remote.model.product.Shipping
import com.joao01sb.mercadolibreapp.data.remote.model.product.VariationData
import com.joao01sb.mercadolibreapp.data.remote.model.search.Paging
import com.joao01sb.mercadolibreapp.data.remote.model.search.Sort
import com.joao01sb.mercadolibreapp.data.remote.response.CategoryResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDescriptionResponse
import com.joao01sb.mercadolibreapp.data.remote.response.ProductDetailResponse
import com.joao01sb.mercadolibreapp.data.remote.response.SearchResponse
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.Product
import com.joao01sb.mercadolibreapp.domain.model.ProductDescription
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail

object MockProductData {

    val mockCompleteSearchResponse = SearchResponse(
        siteId = "MLA",
        countryDefaultTimeZone = "GMT-03:00",
        query = "iphone",
        paging = Paging(
            total = 1000,
            primaryResults = 1000,
            offset = 0,
            limit = 50
        ),
        resultProducts = listOf(
            ResultProduct(
                id = "MLA2005705454",
                title = "iPhone 13 Pro Max 256GB Azul Sierra",
                condition = "new",
                thumbnailId = "987654-MLA2005705454_032023",
                catalogProductId = "MLA-CATALOG-IPHONE13PROMAX",
                listingTypeId = "gold_special",
                sanitizedTitle = "iphone-13-pro-max-256gb-azul-sierra",
                permalink = "https://articulo.mercadolibre.com.ar/MLA-2005705454-iphone-13-pro-max-256gb-azul-sierra-_JM",
                buyingMode = "buy_it_now",
                siteId = "MLA",
                categoryId = "MLA1055",
                domainId = "MLA-SMARTPHONES",
                variationId = "181234567890",
                thumbnail = "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-O.jpg",
                currencyId = "ARS",
                orderBackend = 1,
                price = 899990.0,
                originalPrice = 999990.0,
                salePrice = SalePrice(
                    priceId = "SALE123",
                    amount = 899990.0,
                    conditions = SalePriceConditions(
                        eligible = true,
                        contextRestrictions = listOf("channel_marketplace"),
                        startTime = "2023-07-01T00:00:00Z",
                        endTime = "2023-07-31T23:59:59Z"
                    ),
                    currencyId = "ARS",
                    exchangeRate = null,
                    paymentMethodPrices = emptyList(),
                    paymentMethodType = "TMP",
                    regularAmount = 999990.0,
                    type = "promotion",
                    metadata = SalePriceMetadata(
                        promotionOfferType = "DEAL_OF_THE_DAY",
                        promotionOfferSubType = "AUTOMATIC",
                        promotionId = "PROMO-MLA2005705454-123456",
                        promotionType = "deal_of_the_day"
                    )
                ),
                availableQuantity = 15,
                officialStoreId = 12345,
                officialStoreName = "Apple Store",
                useThumbnailId = true,
                acceptsMercadoPago = true,
                variationFilters = listOf("COLOR", "STORAGE"),
                shipping = Shipping(
                    storePickUp = false,
                    freeShipping = true,
                    logisticType = "fulfillment",
                    mode = "me2",
                    tags = listOf("fulfillment", "self_service_in", "mandatory_free_shipping"),
                    benefits = null,
                    promise = null,
                    shippingScore = -1
                ),
                stopTime = "2044-08-10T04:00:00.000Z",
                seller = Seller(
                    id = 123456789,
                    nickname = "APPLE_STORE_OFICIAL"
                ),
                address = Address(
                    stateId = "AR-B",
                    stateName = "Buenos Aires",
                    cityId = "AR-B-44",
                    cityName = "Buenos Aires"
                ),
                attributes = listOf(
                    ProductAttribute(
                        id = "BRAND",
                        name = "Marca",
                        valueId = "2786791",
                        valueName = "Apple",
                        attributeGroupId = "OTHERS",
                        attributeGroupName = "Otros",
                        valueStruct = null,
                        values = listOf(
                            AttributeValue(
                                id = "2786791",
                                name = "Apple",
                                struct = null,
                                source = 1234567890
                            )
                        ),
                        source = 1234567890,
                        valueType = "string"
                    ),
                    ProductAttribute(
                        id = "MODEL",
                        name = "Modelo",
                        valueId = null,
                        valueName = "13 Pro Max",
                        attributeGroupId = "OTHERS",
                        attributeGroupName = "Otros",
                        valueStruct = null,
                        values = listOf(
                            AttributeValue(
                                id = null,
                                name = "13 Pro Max",
                                struct = null,
                                source = 1234567890
                            )
                        ),
                        source = 1234567890,
                        valueType = "string"
                    )
                ),
                variationsData = mapOf(
                    "181234567890" to VariationData(
                        thumbnail = "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-O.jpg",
                        ratio = "1.0",
                        name = "Azul Sierra",
                        picturesQty = 8,
                        price = 899990.0,
                        inventoryId = "APPLE123456",
                        userProductId = "MLAU123456789",
                        attributes = emptyList(),
                        attributeCombinations = listOf(
                            AttributeCombination(
                                id = "COLOR",
                                name = "Color",
                                valueId = "52049",
                                valueName = "Azul Sierra",
                                valueStruct = null,
                                values = null
                            )
                        )
                    )
                ),
                installments = Installments(
                    quantity = 6,
                    amount = 149998.33,
                    rate = 0.0,
                    currencyId = "ARS",
                    metadata = InstallmentsMetadata(
                        meliplusInstallments = false,
                        additionalBankInterest = false
                    )
                ),
                winnerItemId = null,
                catalogListing = false,
                discounts = null,
                promotionDecorations = null,
                promotions = null,
                inventoryId = null,
                installmentsMotors = null,
                resultType = "item"
            )
        ),
        availableSorts = emptyList(),
        filters = emptyList(),
        availableFilters = emptyList(),
        pdpTracking = null,
        userContext = null,
        sort = Sort(
            id = "relevance",
            name = "Relevancia"
        ),
        rankingIntrospection = emptyMap()
    )

    val mockProductDetailResponse = ProductDetailResponse(
        id = "MLA2005705454",
        siteId = "MLA",
        title = "iPhone 13 Pro Max 256GB Azul Sierra",
        sellerId = 123456789,
        categoryId = "MLA1055",
        officialStoreId = 12345,
        price = 899990.0,
        basePrice = 899990.0,
        originalPrice = 999990.0,
        currencyId = "ARS",
        initialQuantity = 100,
        saleTerms = listOf(
            SaleTerm(
                id = "WARRANTY_TYPE",
                name = "Tipo de garantía",
                valueId = "6150835",
                valueName = "Garantía de fábrica",
                valueStruct = null,
                values = listOf(
                    SaleTermValue(
                        id = "6150835",
                        name = "Garantía de fábrica",
                        struct = null
                    )
                ),
                valueType = "list"
            )
        ),
        buyingMode = "buy_it_now",
        listingTypeId = "gold_special",
        condition = "new",
        permalink = "https://articulo.mercadolibre.com.ar/MLA-2005705454-iphone-13-pro-max-256gb-azul-sierra-_JM",
        thumbnailId = "987654-MLA2005705454_032023",
        thumbnail = "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-I.jpg",
        pictures = listOf(
            ProductPicture(
                id = "987654-MLA2005705454_032023",
                url = "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-O.jpg",
                secureUrl = "https://http2.mlstatic.com/D_987654-MLA2005705454_032023-O.jpg",
                size = "500x500",
                maxSize = "1200x1200",
                quality = "high"
            )
        ),
        videoId = null,
        descriptions = emptyList(),
        acceptsMercadoPago = true,
        nonMercadoPagoPaymentMethods = emptyList(),
        shipping = ProductDetailShipping(
            mode = "me2",
            methods = emptyList(),
            tags = listOf("self_service_in"),
            dimensions = null,
            localPickUp = false,
            freeShipping = true,
            logisticType = "xd_drop_off",
            storePickUp = false
        ),
        internationalDeliveryMode = "none",
        sellerAddress = SellerAddress(
            city = AddressCity(name = "Buenos Aires"),
            state = AddressState(id = "AR-B", name = "Buenos Aires"),
            country = AddressCountry(id = "AR", name = "Argentina"),
            searchLocation = SearchLocation(
                neighborhood = LocationItem(id = "TUxBQlBBTDQyNDVa", name = "Palermo"),
                city = LocationItem(id = "TUxBQ0NBUEBCQTU", name = "Buenos Aires"),
                state = LocationItem(id = "TUxBUENBUGw3M2E1", name = "Buenos Aires")
            ),
            id = 123456789
        ),
        sellerContact = null,
        location = emptyMap(),
        coverageAreas = emptyList(),
        attributes = listOf(
            ProductDetailAttribute(
                id = "BRAND",
                name = "Marca",
                valueId = "2786791",
                valueName = "Apple",
                values = listOf(
                    AttributeValue(
                        id = "2786791",
                        name = "Apple",
                        struct = null,
                        source = 1234567890
                    )
                ),
                valueType = "string"
            ),
            ProductDetailAttribute(
                id = "MODEL",
                name = "Modelo",
                valueId = null,
                valueName = "13 Pro Max",
                values = listOf(
                    AttributeValue(
                        id = null,
                        name = "13 Pro Max",
                        struct = null,
                        source = 1234567890
                    )
                ),
                valueType = "string"
            )
        )
    )

    val mockProductDescriptionResponse = ProductDescriptionResponse(
        text = "iPhone 13 Pro Max com tela Super Retina XDR de 6,7 polegadas",
        plainText = "iPhone 13 Pro Max com tela Super Retina XDR de 6,7 polegadas, sistema de câmera Pro com teleobjetiva",
        lastUpdated = "2023-07-15T10:30:00.000Z",
        dateCreated = "2023-03-15T10:30:00.000Z",
        snapshot = DescriptionSnapshot(
            url = "https://http2.mlstatic.com/resources/frontend/statics/growth-sellers-landings/device-pics/picture-snapshot.png",
            width = 1200,
            height = 800,
            status = "active"
        )
    )

    val mockCategoryResponse = CategoryResponse(
        id = "MLA1055",
        name = "Celulares y Smartphones",
        picture = "https://http2.mlstatic.com/resources/frontend/statics/growth-sellers-landings/device-pics/picture-category.png",
        permalink = "https://listado.mercadolibre.com.ar/celulares-smartphones",
        totalItemsInThisCategory = 25000,
        pathFromRoot = listOf(
            CategoryPath(
                id = "MLA1051",
                name = "Celulares y Teléfonos"
            ),
            CategoryPath(
                id = "MLA1055",
                name = "Celulares y Smartphones"
            )
        ),
        childrenCategories = emptyList(),
        attributeTypes = "required_attributes",
        settings = CategorySettings(
            adultContent = false,
            buyingAllowed = true,
            buyingModes = listOf("buy_it_now", "classified"),
            catalogDomain = "MLA-SMARTPHONES",
            coverageAreas = "not_allowed",
            currencies = listOf("ARS"),
            fragile = false,
            immediatePayment = "required",
            itemConditions = listOf("new", "used"),
            itemsReviewsAllowed = true,
            listingAllowed = true,
            maxDescriptionLength = 50000,
            maxPicturesPerItem = 12,
            maxPicturesPerItemVar = 10,
            maxSubTitleLength = 70,
            maxTitleLength = 60,
            maxVariationsAllowed = 200,
            maximumPrice = null,
            maximumPriceCurrency = "ARS",
            minimumPrice = 1.0,
            minimumPriceCurrency = "ARS",
            mirrorCategory = null,
            mirrorMasterCategory = null,
            mirrorSlaveCategories = emptyList(),
            price = "required",
            reservationAllowed = "not_allowed",
            restrictions = emptyList(),
            roundedAddress = false,
            sellerContact = "not_allowed",
            shippingOptions = listOf("custom", "me2"),
            shippingProfile = "optional",
            showContactInformation = false,
            simpleShipping = "optional",
            stock = "required",
            subVertical = "smartphones",
            subscribable = false,
            tags = emptyList(),
            vertical = "core",
            vipSubdomain = "smartphones",
            buyerProtectionPrograms = listOf("mercado_pago"),
            status = "enabled"
        ),
        channelsSettings = listOf(
            ChannelSettings(
                channel = "marketplace",
                settings = ChannelSettingsDetail(
                    minimumPrice = 1.0,
                    status = "enabled",
                    buyingModes = listOf("buy_it_now"),
                    immediatePayment = "required"
                )
            )
        ),
        metaCategId = null,
        attributable = true,
        dateCreated = "2007-05-08T17:26:28.000Z"
    )

    val mockProducts = listOf(
        Product(
            id = "MLA2005705454",
            title = "iPhone 13 Pro Max 256GB Azul Sierra",
            price = 899990.0,
            currencyId = "ARS",
            thumbnail = "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-O.jpg",
            condition = "new",
            availableQuantity = 15,
            permalink = "https://articulo.mercadolibre.com.ar/MLA-2005705454-iphone-13-pro-max-256gb-azul-sierra-_JM",
            originalPrice = 999990.0,
            freeShipping = true,
            rating = 4.8,
            reviewsCount = 1250,
            isSponsored = false,
            installmentsQuantity = 6,
            installmentsRate = 0.0,
            sellerId = 123456789,
            categoryId = "MLA1055"
        ),
        Product(
            id = "MLA2005705455",
            title = "Samsung Galaxy S21 128GB Preto",
            price = 749990.0,
            currencyId = "ARS",
            thumbnail = "http://http2.mlstatic.com/D_123456-MLA2005705455_032023-O.jpg",
            condition = "new",
            availableQuantity = 20,
            permalink = "https://articulo.mercadolibre.com.ar/MLA-2005705455-samsung-galaxy-s21-128gb-preto-_JM",
            originalPrice = 849990.0,
            freeShipping = true,
            rating = 4.7,
            reviewsCount = 800,
            isSponsored = false,
            installmentsQuantity = 6,
            installmentsRate = 0.0,
            sellerId = 987654321,
            categoryId = "MLA1055"
        ),
        Product(
            id = "MLA2005705456",
            title = "Xiaomi Redmi Note 10 64GB Branco",
            price = 299990.0,
            currencyId = "ARS",
            thumbnail = "http://http2.mlstatic.com/D_654321-MLA2005705456_032023-O.jpg",
            condition = "new",
            availableQuantity = 50,
            permalink = "https://articulo.mercadolibre.com.ar/MLA-2005705456-xiaomi-redmi-note-10-64gb-branco-_JM",
            originalPrice = 349990.0,
            freeShipping = true,
            rating = 4.5,
            reviewsCount = 500,
            isSponsored = false,
            installmentsQuantity = 3,
            installmentsRate = 0.0,
            sellerId = 1234567890,
            categoryId = "MLA1055"
        )
    )

    val mockProductDetail = ProductDetail(
        id = "MLA2005705454",
        title = "iPhone 13 Pro Max 256GB Azul Sierra",
        price = 899990.0,
        originalPrice = 999990.0,
        currencyId = "ARS",
        condition = "new",
        thumbnail = "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-I.jpg",
        pictures = listOf(
            "http://http2.mlstatic.com/D_987654-MLA2005705454_032023-O.jpg"
        ),
        permalink = "https://articulo.mercadolibre.com.ar/MLA-2005705454-iphone-13-pro-max-256gb-azul-sierra-_JM",
        categoryId = "MLA1055",
        sellerId = 123456789,
        acceptsMercadoPago = true,
        freeShipping = true,
        availableQuantity = 100,
        attributes = listOf(
            com.joao01sb.mercadolibreapp.domain.model.ProductAttribute(
                id = "BRAND",
                name = "Marca",
                valueName = "Apple"
            ),
            com.joao01sb.mercadolibreapp.domain.model.ProductAttribute(
                id = "MODEL",
                name = "Modelo",
                valueName = "13 Pro Max"
            )
        ),
        rating = 4.8,
        reviewsCount = 1250,
        warranty = "Garantía de fábrica: 12 meses",
        brand = "Apple",
        model = "13 Pro Max",
        installmentsQuantity = 6,
        installmentsRate = 0.0,
        tags = listOf("good_quality_picture", "immediate_payment")
    )

    val mockProductDescription = ProductDescription(
        plainText = "iPhone 13 Pro Max com tela Super Retina XDR de 6,7 polegadas, sistema de câmera Pro com teleobjetiva",
        text = "iPhone 13 Pro Max com tela Super Retina XDR de 6,7 polegadas, sistema de câmera Pro com teleobjetiva"
    )

    val mockCategory = Category(
        id = "MLA1055",
        name = "Celulares y Smartphones",
        picture = "https://http2.mlstatic.com/resources/frontend/statics/growth-sellers-landings/device-pics/picture-category.png",
        totalItems = 25000
    )
}