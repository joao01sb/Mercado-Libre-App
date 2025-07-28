package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchUiData
import com.joao01sb.mercadolibreapp.presentation.util.UiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val emptyState = UiState.Success(
        SearchUiData(
            searchQuery = "",
            recentSearches = emptyList()
        )
    )

    private val stateWithRecents = UiState.Success(
        SearchUiData(
            searchQuery = "",
            recentSearches = listOf("iPhone", "Samsung")
        )
    )

    @Test
    fun searchScreen_shouldDisplayPlaceholderText() {
        composeTestRule.setContent {
            SearchScreen(
                state = emptyState,
                onChangedQuery = {},
                onSearch = {},
                onRecentSearchClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("search_text_field")
            .assertIsDisplayed()
    }

    @Test
    fun searchScreen_shouldNavigateToHome_whenBackButtonClicked() {
        var backClicked = false

        composeTestRule.setContent {
            SearchScreen(
                state = emptyState,
                onChangedQuery = {},
                onSearch = {},
                onRecentSearchClick = {},
                onBackClick = { backClicked = true }
            )
        }

        composeTestRule
            .onNodeWithTag("back_button")
            .performClick()

        assert(backClicked)
    }

    @Test
    fun searchScreen_shouldDisplayRecentSearches() {
        composeTestRule.setContent {
            SearchScreen(
                state = stateWithRecents,
                onChangedQuery = {},
                onSearch = {},
                onRecentSearchClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("iPhone")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Samsung")
            .assertIsDisplayed()
    }

    @Test
    fun searchScreen_shouldSetTextInTextField_whenRecentSearchClicked() {
        var selectedQuery = ""

        composeTestRule.setContent {
            SearchScreen(
                state = stateWithRecents,
                onChangedQuery = {},
                onSearch = {},
                onRecentSearchClick = { selectedQuery = it },
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("iPhone")
            .performClick()

        assert(selectedQuery == "iPhone")
    }

    @Test
    fun searchScreen_shouldCallOnSearch_whenSearchButtonClicked() {
        var searchCalled = false
        val stateWithQuery = UiState.Success(
            SearchUiData(
                searchQuery = "Test Query",
                recentSearches = emptyList()
            )
        )

        composeTestRule.setContent {
            SearchScreen(
                state = stateWithQuery,
                onChangedQuery = {},
                onSearch = { searchCalled = true },
                onRecentSearchClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("search_text_field")
            .performImeAction()

        assert(searchCalled)
    }
}
