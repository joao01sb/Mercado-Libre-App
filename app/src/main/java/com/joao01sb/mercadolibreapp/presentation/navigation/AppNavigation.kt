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
                    navController.navigate(SearchRoute)
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
                    searchViewModel.saveSearchQuery()
                    navController.navigate(SearchResultsRoute(uiState.searchQuery))
                }
            )
        }

        composable<SearchResultsRoute> {
            val searchResultsViewModel: SearchResultsViewModel = hiltViewModel()
            val uiState by searchResultsViewModel.uiState.collectAsState()

            SearchResultsScreen(
                state = uiState,
                onProductClick = { productId ->
                    navController.navigate(ProductDetailRoute(productId = productId, query = uiState.query))
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    navController.navigate(SearchRoute)
                }
            )
        }

        composable<ProductDetailRoute> {
            val productDetailViewModel: ProductDetailViewModel = hiltViewModel()
            val uiState by productDetailViewModel.uiState.collectAsState()

            uiState.productDetail?.let { productDetail ->
                ProductDetailScreen(
                    productDetail = productDetail,
                    query = productDetailViewModel.query,
                    productDescription = uiState.productDescription,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSearchClick = {
                        navController.navigate(SearchRoute)
                    }
                )
            }
        }
    }
}
