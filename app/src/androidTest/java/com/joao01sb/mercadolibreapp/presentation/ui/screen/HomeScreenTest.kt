package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.joao01sb.mercadolibreapp.R
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_shouldDisplaySearchBar() {
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule
            .onNodeWithTag("search_bar")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplaySearchMercadoLibreLabel() {
        composeTestRule.setContent {
            HomeScreen()
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(R.string.search_mercado_libre)

        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldBeDisplayedCorrectly() {
        var searchClicked = false

        composeTestRule.setContent {
            HomeScreen(
                onSearchClick = { searchClicked = true }
            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(R.string.search_mercado_libre)

        composeTestRule
            .onNodeWithTag("search_bar")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldNavigateToSearchScreenWhenSearchBarClicked() {
        var searchClicked = false

        composeTestRule.setContent {
            HomeScreen(
                onSearchClick = { searchClicked = true }
            )
        }

        composeTestRule
            .onNodeWithTag("search_bar_input")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("search_bar_input")
            .performClick()

        assertTrue("Should navigate to SearchScreen when search bar is clicked", searchClicked)
    }
}
