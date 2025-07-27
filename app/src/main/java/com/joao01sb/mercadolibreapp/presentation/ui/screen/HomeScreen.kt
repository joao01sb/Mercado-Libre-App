package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joao01sb.mercadolibreapp.presentation.ui.components.SearchBar

@Preview
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            onSearchClick = onSearchClick
        )
    }

}