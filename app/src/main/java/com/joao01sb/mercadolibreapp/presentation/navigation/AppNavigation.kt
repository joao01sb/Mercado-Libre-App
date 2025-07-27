package com.joao01sb.mercadolibreapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joao01sb.mercadolibreapp.presentation.navigation.routes.HomeRoute
import com.joao01sb.mercadolibreapp.presentation.navigation.routes.ProductDetailRoute
import com.joao01sb.mercadolibreapp.presentation.navigation.routes.SearchResultsRoute
import com.joao01sb.mercadolibreapp.presentation.navigation.routes.SearchRoute
import com.joao01sb.mercadolibreapp.presentation.ui.screen.HomeScreen
import com.joao01sb.mercadolibreapp.presentation.ui.screen.ProductDetailScreen
import com.joao01sb.mercadolibreapp.presentation.ui.screen.SearchResultsScreen
import com.joao01sb.mercadolibreapp.presentation.ui.screen.SearchScreen
import com.joao01sb.mercadolibreapp.presentation.ui.viewmodel.ProductDetailViewModel
import com.joao01sb.mercadolibreapp.presentation.ui.viewmodel.SearchResultsViewModel
import com.joao01sb.mercadolibreapp.presentation.ui.viewmodel.SearchViewModel
import com.joao01sb.mercadolibreapp.presentation.util.UiState

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onSearchClick = {
                    navController.navigate(SearchRoute())
                }
            )
        }

        composable<SearchRoute> {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val uiState by searchViewModel.uiState.collectAsState()

            SearchScreen(
                state = uiState,
                onBackClick = {
                    navController.popBackStack()
                },
                onChangedQuery = { newQuery ->
                    searchViewModel.changedSearchProducts(newQuery)
                },
                onSearch = {
                    when (val currentState = uiState) {
                        is UiState.Success -> {
                            val query = currentState.data
                            if (query.searchQuery.isNotBlank()) {
                                searchViewModel.saveSearch(query.searchQuery)
                                navController.navigate(SearchResultsRoute(query = query.searchQuery)) {
                                    popUpTo(HomeRoute) {
                                        saveState = false
                                    }
                                }
                            }
                        }
                        else -> {}
                    }
                },
                onRecentSearchClick = { query ->
                    searchViewModel.changedSearchProducts(query)
                    navController.navigate(SearchResultsRoute(query = query)) {
                        popUpTo(HomeRoute) {
                            saveState = false
                        }
                    }
                }
            )
        }

        composable<SearchResultsRoute> { backStackEntry ->
            val searchResultsViewModel: SearchResultsViewModel = hiltViewModel()
            val uiState by searchResultsViewModel.uiState.collectAsState()

            SearchResultsScreen(
                state = uiState,
                query = searchResultsViewModel.currentQuery ,
                onProductClick = { productId, productJson ->
                    navController.navigate(
                        ProductDetailRoute(
                            productId = productId,
                            query = when (val currentState = uiState) {
                                is UiState.Success -> currentState.data.query
                                else -> ""
                            },
                            productJson = productJson
                        )
                    )
                },
                onBackClick = {
                    navController.popBackStack(HomeRoute, inclusive = false)
                },
                onSearchClick = {
                    navController.navigate(SearchRoute(searchResultsViewModel.currentQuery))
                },
                onRetryClick = {
                    searchResultsViewModel.retrySearch()
                }
            )
        }

        composable<ProductDetailRoute> {
            val productDetailViewModel: ProductDetailViewModel = hiltViewModel()
            val uiState by productDetailViewModel.uiState.collectAsState()

            ProductDetailScreen(
                state = uiState,
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    navController.navigate(SearchRoute())
                },
                onRetryClick = {
                    productDetailViewModel.retryLoadProduct()
                }
            )
        }
    }
}
