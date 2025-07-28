package com.joao01sb.mercadolibreapp.data.mock

object ApiServiceMockData {

    const val VALID_SEARCH_JSON = """
        {
            "site_id": "MLA",
            "country_default_time_zone": "GMT-03:00",
            "query": "arroz",
            "paging": {
                "total": 3678,
                "primary_results": 1000,
                "offset": 0,
                "limit": 50
            },
            "results": [
                {
                    "id": "MLA1411360722",
                    "title": "Arroz Parboil Dos Hermanos Libre Gluten Sin Tacc No Se Pega",
                    "condition": "new",
                    "thumbnail_id": "805370-MLA43144907870_082020",
                    "catalog_product_id": "MLA19936763",
                    "listing_type_id": "gold_special",
                    "sanitized_title": "",
                    "permalink": "https://www.mercadolibre.com.ar/arroz-parboil-dos-hermanos-libre-gluten-sin-tacc-no-se-pega/p/MLA19936763#wid=MLA1411360722&sid=unknown",
                    "buying_mode": "buy_it_now",
                    "site_id": "MLA",
                    "category_id": "MLA389311",
                    "domain_id": "MLA-RICE",
                    "variation_id": null,
                    "thumbnail": "http://http2.mlstatic.com/D_805370-MLA43144907870_082020-I.jpg",
                    "currency_id": "ARS",
                    "order_backend": 1,
                    "price": 2699.55,
                    "original_price": 5999.0,
                    "sale_price": {
                        "price_id": "",
                        "amount": 2699.55,
                        "conditions": {
                            "eligible": true,
                            "context_restrictions": ["channel_marketplace"],
                            "start_time": "2025-03-12T03:00:00Z",
                            "end_time": "2025-04-12T02:59:59Z"
                        },
                        "currency_id": "ARS",
                        "exchange_rate": null,
                        "payment_method_prices": [],
                        "payment_method_type": "TMP",
                        "regular_amount": 5999.0,
                        "type": "promotion",
                        "metadata": {
                            "promotion_type": "custom",
                            "promotion_offer_type": "CUSTOM",
                            "promotion_id": "OFFER-MLA1411360722-10263334316"
                        }
                    },
                    "available_quantity": 50,
                    "official_store_id": 106348,
                    "official_store_name": "Capsuland",
                    "use_thumbnail_id": true,
                    "accepts_mercadopago": true,
                    "variation_filters": [],
                    "shipping": {
                        "store_pick_up": false,
                        "free_shipping": false,
                        "logistic_type": "fulfillment",
                        "mode": "me2",
                        "tags": ["fulfillment", "self_service_in"],
                        "benefits": null,
                        "promise": null,
                        "shipping_score": -1
                    },
                    "stop_time": "2043-05-01T04:00:00.000Z",
                    "seller": {
                        "id": 57995397,
                        "nickname": "CAPSULANDIA"
                    },
                    "address": {
                        "state_id": "AR-C",
                        "state_name": "Capital Federal",
                        "city_id": "TUxBQkJFTDcyNTJa",
                        "city_name": "Belgrano"
                    },
                    "attributes": [
                        {
                            "id": "BRAND",
                            "name": "Marca",
                            "value_id": "12179114",
                            "value_name": "Dos Hermanos",
                            "attribute_group_id": "OTHERS",
                            "attribute_group_name": "Otros",
                            "value_struct": null,
                            "values": [
                                {
                                    "id": "12179114",
                                    "name": "Dos Hermanos",
                                    "struct": null,
                                    "source": 1
                                }
                            ],
                            "source": 1,
                            "value_type": "string"
                        }
                    ],
                    "variations_data": {},
                    "installments": null,
                    "winner_item_id": null,
                    "catalog_listing": false,
                    "discounts": null,
                    "promotion_decorations": null,
                    "promotions": null,
                    "inventory_id": null,
                    "installments_motors": null,
                    "result_type": "item"
                }
            ],
            "sort": {
                "id": "relevance",
                "name": "Relevância"
            },
            "available_sorts": [
                {
                    "id": "relevance",
                    "name": "Relevância"
                }
            ],
            "filters": [],
            "available_filters": [],
            "pdp_tracking": null
        }
    """

    const val VALID_PRODUCT_DETAIL_JSON = """
        {
            "id": "MLA1411360722",
            "site_id": "MLA",
            "title": "Arroz Parboil Dos Hermanos Libre Gluten Sin Tacc No Se Pega",
            "seller_id": 57995397,
            "category_id": "MLA389311",
            "official_store_id": 106348,
            "price": 2699.55,
            "base_price": 2699.55,
            "original_price": 5999.0,
            "currency_id": "ARS",
            "initial_quantity": 9465,
            "sale_terms": [
                {
                    "id": "INVOICE",
                    "name": "Facturação",
                    "value_id": "6891885",
                    "value_name": "Factura A",
                    "value_struct": null,
                    "values": [
                        {
                            "id": "6891885",
                            "name": "Factura A",
                            "struct": null
                        }
                    ],
                    "value_type": "list"
                }
            ],
            "buying_mode": "buy_it_now",
            "listing_type_id": "gold_special",
            "condition": "new",
            "permalink": "https://www.mercadolibre.com.ar/arroz-parboil-dos-hermanos-libre-gluten-sin-tacc-no-se-pega/p/MLA19936763#wid=MLA1411360722&sid=unknown",
            "thumbnail_id": "805370-MLA43144907870_082020",
            "thumbnail": "http://http2.mlstatic.com/D_805370-MLA43144907870_082020-I.jpg",
            "pictures": [
                {
                    "id": "805370-MLA43144907870_082020",
                    "url": "http://http2.mlstatic.com/D_805370-MLA43144907870_082020-O.jpg",
                    "secure_url": "https://http2.mlstatic.com/D_805370-MLA43144907870_082020-O.jpg",
                    "size": "500x500",
                    "max_size": "1200x1200",
                    "quality": ""
                }
            ],
            "video_id": null,
            "descriptions": [],
            "accepts_mercadopago": true,
            "non_mercado_pago_payment_methods": [],
            "shipping": {
                "mode": "me2",
                "methods": [],
                "tags": ["fulfillment", "self_service_in"],
                "dimensions": null,
                "local_pick_up": false,
                "free_shipping": false,
                "logistic_type": "fulfillment",
                "store_pick_up": false
            },
            "international_delivery_mode": "none",
            "seller_address": {
                "address_line": "",
                "zip_code": "",
                "city": {
                    "id": "TUxBQkJFTDcyNTJa",
                    "name": "Belgrano"
                },
                "state": {
                    "id": "AR-C",
                    "name": "Capital Federal"
                },
                "country": {
                    "id": "AR",
                    "name": "Argentina"
                },
                "latitude": "",
                "longitude": "",
                "search_location": {
                    "neighborhood": {
                        "id": "",
                        "name": ""
                    },
                    "city": {
                        "id": "TUxBQkJFTDcyNTJa",
                        "name": "Belgrano"
                    },
                    "state": {
                        "id": "AR-C",
                        "name": "Capital Federal"
                    }
                },
                "id": 57995397
            },
            "seller_contact": null,
            "location": {},
            "coverage_areas": [],
            "attributes": [
                {
                    "id": "BRAND",
                    "name": "Marca",
                    "value_id": "12179114",
                    "value_name": "Dos Hermanos",
                    "attribute_group_id": "OTHERS",
                    "attribute_group_name": "Otros",
                    "value_struct": null,
                    "values": [
                        {
                            "id": "12179114",
                            "name": "Dos Hermanos",
                            "struct": null,
                            "source": 1
                        }
                    ],
                    "source": 1,
                    "value_type": "string"
                }
            ]
        }
    """

    const val VALID_DESCRIPTION_JSON = """
        {
            "text": "Arroz Parboil Dos Hermanos - Libre de Gluten, Sin TACC, No se pega. Ideal para dietas especiais e preparações que requerem um arroz de qualidade premium.",
            "plain_text": "Arroz Parboil Dos Hermanos - Libre de Gluten, Sin TACC, No se pega. Ideal para dietas especiais e preparações que requerem um arroz de qualidade premium.",
            "last_updated": "2023-05-06T18:43:33.205Z",
            "date_created": "2023-05-06T18:43:33.205Z",
            "snapshot": {
                "url": "http://descriptions.mlstatic.com/D-MLA1411360722.jpg?hash=8520c3b8559cb08aa7e782b8f5334ffe_0x0",
                "width": 0,
                "height": 0,
                "status": ""
            }
        }
    """

    const val VALID_CATEGORY_JSON = """
        {
            "id": "MLA389311",
            "name": "Arroz",
            "picture": "https://http2.mlstatic.com/resources/frontend/statics/growth-sellers-landings/device-pictures/picture-arroz.png",
            "permalink": "https://listado.mercadolibre.com.ar/arroz",
            "total_items_in_this_category": 3678,
            "path_from_root": [
                {
                    "id": "MLA1403",
                    "name": "Alimentos y Bebidas"
                },
                {
                    "id": "MLA389311",
                    "name": "Arroz"
                }
            ],
            "children_categories": [],
            "attribute_types": "attributes",
            "settings": {
                "adult_content": false,
                "buying_allowed": true,
                "buying_modes": ["buy_it_now", "auction"],
                "catalog_domain": "MLA-RICE",
                "coverage_areas": "not_allowed",
                "currencies": ["ARS"],
                "fragile": false,
                "immediate_payment": "required",
                "item_conditions": ["not_specified", "new", "used"],
                "items_reviews_allowed": false,
                "listing_allowed": true,
                "max_description_length": 50000,
                "max_pictures_per_item": 12,
                "max_pictures_per_item_var": 10,
                "max_sub_title_length": 70,
                "max_title_length": 60,
                "max_variations_allowed": 5,
                "maximum_price": 999999999.0,
                "maximum_price_currency": "ARS",
                "minimum_price": 0.0,
                "minimum_price_currency": "ARS",
                "mirror_category": null,
                "mirror_master_category": null,
                "mirror_slave_categories": [],
                "price": "required",
                "reservation_allowed": "not_allowed",
                "restrictions": [],
                "rounded_address": false,
                "seller_contact": "not_allowed",
                "shipping_options": ["custom"],
                "shipping_profile": "optional",
                "show_contact_information": false,
                "simple_shipping": "optional",
                "stock": "optional",
                "sub_vertical": "none",
                "subscribable": false,
                "tags": [],
                "vertical": "marketplace",
                "vip_subdomain": "arroz",
                "buyer_protection_programs": ["GUARANTEE_PROGRAM"],
                "status": "enabled"
            },
            "channels_settings": [
                {
                    "channel": "marketplace",
                    "settings": {
                        "minimum_price": 0.0,
                        "status": "enabled",
                        "buying_modes": ["buy_it_now"],
                        "immediate_payment": "required"
                    }
                }
            ],
            "meta_categ_id": "MLA1403",
            "attributable": true,
            "date_created": "2019-02-07T09:00:00.000Z"
        }
    """

    const val MALFORMED_SEARCH_JSON = """
        {
            "site_id": "MLA",
            "query": "iphone",
            "results": [
                {
                    "id": "MLA123456789",
                    "title": "iPhone incomplete"
                }
            ]
        """

    const val MALFORMED_PRODUCT_DETAIL_JSON = """
        {
            "id": "MLA123456789",
            "title": "iPhone 13 Pro Max",
            "price": "invalid_price_type",
            "seller_id": "invalid_seller_id"
        """

    const val MALFORMED_DESCRIPTION_JSON = """
        {
            "text": "Description",
            "plain_text": 123,
            "date_created": "invalid_date_format"
        """

    const val MALFORMED_CATEGORY_JSON = """
        {
            "id": "MLA1055",
            "name": "Category",
            "total_items_in_this_category": "invalid_number",
            "settings": {
                "buying_allowed": "invalid_boolean",
                "buying_modes": "should_be_array"
            }
        """

    object TestData {
        const val VALID_PRODUCT_ID = "MLA1411360722"
        const val VALID_CATEGORY_ID = "MLA389311"
        const val VALID_QUERY = "arroz"

        const val INVALID_PRODUCT_ID = "INVALID123"
        const val NONEXISTENT_QUERY = "nonexistent"

        const val EXPECTED_SITE_ID = "MLA"
        const val EXPECTED_PRODUCT_TITLE = "Arroz Parboil Dos Hermanos Libre Gluten Sin Tacc No Se Pega"
        const val EXPECTED_PRODUCT_PRICE = 2699.55
        const val EXPECTED_CATEGORY_NAME = "Arroz"
        const val EXPECTED_CATEGORY_TOTAL_ITEMS = 3678

        const val ANOTHER_VALID_PRODUCT_ID = "MLA1884722608"
        const val MLB_PRODUCT_ID = "MLB3128412969"

        const val IPHONE_QUERY = "iphone"
        const val CAFE_QUERY = "cafe"
        const val CAMISA_QUERY = "camisa"
        const val ZAPATILLAS_QUERY = "zapatillas"
    }

    object AssetPaths {
        fun searchFile(query: String) = "searches/search-MLA-$query.json"
        fun productDetailFile(productId: String) = "itens/item-$productId.json"
        fun productDescriptionFile(productId: String) = "descriptions/item-$productId-description.json"
        fun categoryFile(categoryId: String) = "categories/item-$categoryId-category.json"
    }

    object LogMessages {
        fun searchingProducts(query: String) = "Searching products with query: '$query'"
        fun gettingProductDetails(productId: String) = "Getting product details for ID: '$productId'"
        fun gettingProductDescription(productId: String) = "Getting product description for ID: '$productId'"
        fun gettingCategory(categoryId: String) = "Getting category for ID: '$categoryId'"

        fun errorSearchingProducts(query: String) = "Error searching products for query: '$query'"
        fun errorGettingProductDetails(productId: String) = "Error getting product details for ID: '$productId'"
        fun errorGettingProductDescription(productId: String) = "Error getting product description for ID: '$productId'"
        fun errorGettingCategory(categoryId: String) = "Error getting category for ID: '$categoryId'"
    }

    object ExceptionMessages {
        const val SEARCH_PRODUCTS_MOCK_UNAVAILABLE = "Failed to search products - Mock data not available"
        const val PRODUCT_DETAILS_INVALID_JSON = "Failed to get product details - Invalid JSON format"
        const val PRODUCT_DESCRIPTION_UNEXPECTED_ERROR = "Failed to get product description - Unexpected error"
        const val CATEGORY_MOCK_UNAVAILABLE = "Failed to get category - Mock data not available"
    }
}