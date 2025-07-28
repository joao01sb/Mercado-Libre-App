package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.joao01sb.mercadolibreapp.R
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchResultsUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchResultsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var successState: UiState<SearchResultsUiData>
    private lateinit var emptyState: UiState<SearchResultsUiData>
    private lateinit var loadingState: UiState<SearchResultsUiData>
    private lateinit var errorState: UiState<SearchResultsUiData>

    @Before
    fun setUp() {
        successState = UiState.Success(
            SearchResultsUiData(
                query = "celular",
                products = ProductDataTest.mockProducts,
                hasSearched = true
            )
        )

        emptyState = UiState.Success(
            SearchResultsUiData(
                query = "produtoinexistente",
                products = emptyList(),
                hasSearched = true
            )
        )

        loadingState = UiState.Loading

        errorState = UiState.Error("Erro de conexão")
    }

    @After
    fun tearDown() {
        composeTestRule.waitForIdle()
    }

    @Test
    fun searchResultsScreen_shouldDisplaySearchBarWithQuery() {
        composeTestRule.setContent {
            SearchResultsScreen(
                state = successState,
                query = "celular",
                onProductClick = { _, _ -> },
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNode(hasTestTag("search_bar"))
            .assertExists()

        composeTestRule
            .onNodeWithText("celular", substring = true, ignoreCase = true)
            .assertExists()
    }

    @Test
    fun searchResultsScreen_shouldNavigateBack_whenBackButtonPressed() {
        var backClicked = false

        composeTestRule.setContent {
            SearchResultsScreen(
                state = successState,
                query = "celular",
                onProductClick = { _, _ -> },
                onBackClick = { backClicked = true },
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNode(hasTestTag("back_button"))
            .performClick()

        assert(backClicked)
    }

    @Test
    fun searchResultsScreen_shouldNavigateToSearch_whenSearchBarClicked() {
        var searchClicked = false

        composeTestRule.setContent {
            SearchResultsScreen(
                state = successState,
                query = "celular",
                onProductClick = { _, _ -> },
                onBackClick = {},
                onSearchClick = { searchClicked = true },
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNode(hasTestTag("search_bar"))
            .performClick()

        assert(searchClicked)
    }

    @Test
    fun searchResultsScreen_shouldDisplayEmptyMessage_whenNoProductsFound() {
        composeTestRule.setContent {
            SearchResultsScreen(
                state = emptyState,
                query = "produtoinexistente",
                onProductClick = { _, _ -> },
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithText(context.getString(R.string.no_results_title))
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun searchResultsScreen_shouldDisplayLoadingIndicator_whenLoading() {
        composeTestRule.setContent {
            SearchResultsScreen(
                state = loadingState,
                query = "celular",
                onProductClick = { _, _ -> },
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertExists()
    }

    @Test
    fun searchResultsScreen_shouldDisplayProducts_whenStateIsSuccess() {
        composeTestRule.setContent {
            SearchResultsScreen(
                state = successState,
                query = "celular",
                onProductClick = { _, _ -> },
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText("iPhone", substring = true)
            .assertExists()

        val priceNodes = composeTestRule
            .onAllNodesWithText("$", substring = true)
            .fetchSemanticsNodes()

        assert(priceNodes.isNotEmpty()) { "Should have at least one product with price displayed" }
    }

    @Test
    fun searchResultsScreen_shouldCallProductClick_whenProductIsClicked() {
        var clickedProductId = ""
        var callbackExecuted = false

        composeTestRule.setContent {
            SearchResultsScreen(
                state = successState,
                query = "celular",
                onProductClick = { productId, json ->
                    clickedProductId = productId
                    callbackExecuted = true
                },
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText("iPhone", substring = true, ignoreCase = true)
            .assertExists()
            .performClick()

        composeTestRule.waitForIdle()

        assert(callbackExecuted) { "Callback should have been executed" }
        assert(clickedProductId.isNotEmpty()) { "Product ID should not be empty, got: '$clickedProductId'" }
    }

    @Test
    fun searchResultsScreen_shouldCallRetryClick_whenRetryButtonPressed() {
        var retryClicked = false

        composeTestRule.setContent {
            SearchResultsScreen(
                state = errorState,
                query = "celular",
                onProductClick = { _, _ -> },
                onBackClick = {},
                onSearchClick = {},
                onRetryClick = { retryClicked = true }
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.try_again))
            .performClick()

        assert(retryClicked)
    }
}
