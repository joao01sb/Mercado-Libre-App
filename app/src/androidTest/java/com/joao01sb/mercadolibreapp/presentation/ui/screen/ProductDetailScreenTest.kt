package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.joao01sb.mercadolibreapp.presentation.ui.state.ProductDetailUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
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
        successStateWithDescription = UiState.Success(ProductDataTest.mockProductDetailUiDataWithDescription)
        successStateWithoutDescription = UiState.Success(ProductDataTest.mockProductDetailUiDataWithoutDescription)
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

        composeTestRule
            .onNodeWithText("iphone", substring = true, ignoreCase = true)
            .assertExists()

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

        composeTestRule
            .onNodeWithText("iPhone 13 Pro Max", substring = true, ignoreCase = true)
            .assertExists()
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

        composeTestRule
            .onNodeWithText("Descrição")
            .assertDoesNotExist()

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

        val priceNodes = composeTestRule
            .onAllNodesWithText("$", substring = true)
            .fetchSemanticsNodes()

        assert(priceNodes.isNotEmpty()) { "Product price should be displayed" }
    }
}
