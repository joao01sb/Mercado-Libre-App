package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.joao01sb.mercadolibreapp.R
import com.joao01sb.mercadolibreapp.presentation.ui.state.ProductDetailUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import com.joao01sb.mercadolibreapp.domain.model.Category
import com.joao01sb.mercadolibreapp.domain.model.ProductAttribute
import com.joao01sb.mercadolibreapp.domain.model.ProductDetail
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var successStateWithDescription: UiState<ProductDetailUiData>
    private lateinit var successStateWithoutDescription: UiState<ProductDetailUiData>
    private lateinit var loadingState: UiState<ProductDetailUiData>

    @Before
    fun setUp() {
        // Usando o produto do ProductDataTest
        val mockProduct = ProductDataTest.mockProducts.first()

        // Criando ProductDetail baseado no produto do ProductDataTest
        val mockProductDetail = ProductDetail(
            id = mockProduct.id,
            title = mockProduct.title,
            price = mockProduct.price,
            originalPrice = mockProduct.originalPrice,
            currencyId = mockProduct.currencyId,
            condition = mockProduct.condition,
            thumbnail = mockProduct.thumbnail,
            pictures = listOf(
                mockProduct.thumbnail,
                "https://example.com/iphone2.jpg",
                "https://example.com/iphone3.jpg"
            ),
            permalink = mockProduct.permalink,
            categoryId = mockProduct.categoryId ?: "MLA1055",
            sellerId = mockProduct.sellerId ?: 123456789,
            acceptsMercadoPago = true,
            freeShipping = mockProduct.freeShipping,
            availableQuantity = mockProduct.availableQuantity,
            attributes = listOf(
                ProductAttribute(id = "BRAND", name = "Marca", valueName = "Apple"),
                ProductAttribute(id = "MODEL", name = "Modelo", valueName = "13 Pro Max")
            ),
            rating = mockProduct.rating,
            reviewsCount = mockProduct.reviewsCount,
            warranty = "Garantía de fábrica: 12 meses",
            brand = "Apple",
            model = "13 Pro Max",
            installmentsQuantity = mockProduct.installmentsQuantity,
            installmentsRate = mockProduct.installmentsRate,
            tags = listOf("good_quality_picture", "immediate_payment")
        )

        val mockCategory = Category(
            id = "MLA1055",
            name = "Celulares y Smartphones",
            picture = "https://example.com/category.png",
            totalItems = 25000
        )

        successStateWithDescription = UiState.Success(
            ProductDetailUiData(
                baseProduct = mockProduct,
                productDetail = mockProductDetail,
                description = "Esta é uma descrição detalhada do produto ${mockProduct.title} com todas as especificações técnicas.",
                category = mockCategory,
                query = "iphone"
            )
        )

        successStateWithoutDescription = UiState.Success(
            ProductDetailUiData(
                baseProduct = mockProduct,
                productDetail = mockProductDetail,
                description = null,
                category = mockCategory,
                query = "iphone"
            )
        )

        loadingState = UiState.Loading
    }

    @After
    fun tearDown() {
        composeTestRule.waitForIdle()
    }

    @Test
    fun detailsScreen_shouldNavigateBack_whenBackButtonPressed() {
        var backClicked = false

        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithDescription,
                onBackClick = { backClicked = true },
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Clica no botão back
        composeTestRule
            .onNode(hasTestTag("back_button"))
            .performClick()

        assert(backClicked) { "Back button should trigger navigation back to SearchResults" }
    }

    @Test
    fun detailsScreen_shouldDisplayDefaultSearchLabel_inSearchBar() {
        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithDescription,
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Verifica se a search bar contém o query
        composeTestRule
            .onNodeWithText("iphone", substring = true, ignoreCase = true)
            .assertExists()

        // Verifica se a search bar existe
        composeTestRule
            .onNode(hasTestTag("search_bar"))
            .assertExists()
    }

    @Test
    fun detailsScreen_shouldDisplayProductName_whenProductIsLoaded() {
        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithDescription,
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Verifica se o nome do produto aparece na tela
        composeTestRule
            .onNodeWithText("iPhone 13 Pro Max", substring = true, ignoreCase = true)
            .assertExists()
    }

    @Test
    fun detailsScreen_shouldDisplayDescription_whenDescriptionExists() {
        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithDescription,
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Primeiro, vamos verificar se há uma LazyColumn (que contém todos os elementos)
        composeTestRule
            .onNode(hasScrollAction())
            .assertExists()

        // Scroll para baixo para encontrar a seção de descrição
        repeat(5) {
            composeTestRule
                .onNode(hasScrollAction())
                .performScrollToIndex(it)
            composeTestRule.waitForIdle()
        }

        // Tenta encontrar a seção de descrição usando diferentes estratégias
        val descriptionFound = try {
            // Primeira tentativa: procurar pelo título "Descrição"
            composeTestRule
                .onNodeWithText("Descrição")
                .assertExists()
            true
        } catch (e: AssertionError) {
            try {
                // Segunda tentativa: procurar pelo conteúdo da descrição
                composeTestRule
                    .onNodeWithText("Esta é uma descrição detalhada", substring = true)
                    .assertExists()
                true
            } catch (e2: AssertionError) {
                false
            }
        }

        // Verifica se pelo menos uma das verificações passou
        assert(descriptionFound) { "Neither description title nor content was found on screen" }
    }

    @Test
    fun detailsScreen_shouldNotDisplayDescription_whenDescriptionIsNull() {
        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithoutDescription,
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Verifica se o título da seção de descrição NÃO aparece quando não há descrição
        composeTestRule
            .onNodeWithText("Descrição")
            .assertDoesNotExist()

        // Verifica que em vez da descrição, aparece a seção de informações básicas do produto
        // (que seria exibida quando não há descrição)
        composeTestRule
            .onNodeWithText("iPhone 13 Pro Max", substring = true)
            .assertExists()
    }

    @Test
    fun detailsScreen_shouldNavigateToSearch_whenSearchBarClicked() {
        var searchClicked = false

        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithDescription,
                onBackClick = {},
                onSearchClick = { searchClicked = true },
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Clica na search bar
        composeTestRule
            .onNode(hasTestTag("search_bar"))
            .performClick()

        assert(searchClicked) { "Search bar click should trigger navigation to SearchScreen" }
    }

    @Test
    fun detailsScreen_shouldDisplayLoadingIndicator_whenLoading() {
        composeTestRule.setContent {
            ProductDetailScreen(
                state = loadingState,
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Verifica se o indicador de loading aparece
        composeTestRule
            .onNode(hasProgressBarRangeInfo(androidx.compose.ui.semantics.ProgressBarRangeInfo.Indeterminate))
            .assertExists()
    }

    @Test
    fun detailsScreen_shouldDisplayProductPrice_whenProductIsLoaded() {
        composeTestRule.setContent {
            ProductDetailScreen(
                state = successStateWithDescription,
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        // Verifica se o preço do produto aparece (procura pelo símbolo de dinheiro)
        val priceNodes = composeTestRule
            .onAllNodesWithText("$", substring = true)
            .fetchSemanticsNodes()

        assert(priceNodes.isNotEmpty()) { "Product price should be displayed" }
    }
}
