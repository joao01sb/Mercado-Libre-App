package com.joao01sb.mercadolibreapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joao01sb.mercadolibreapp.R
import com.joao01sb.mercadolibreapp.presentation.ui.components.SearchHistoryItem
import com.joao01sb.mercadolibreapp.presentation.ui.state.SearchUiState


@Preview
@Composable
fun SearchScreen(
    state: SearchUiState = SearchUiState(),
    onBackClick: () -> Unit = {},
    onChangedQuery: (String) -> Unit = {},
    onSearch: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 1.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.Black
                    )
                }

                TextField(
                    value = state.searchQuery,
                    onValueChange = { newValue ->
                        onChangedQuery(newValue)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_placeholder),
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (state.searchQuery.isNotBlank()) {
                                keyboardController?.hide()
                                onSearch()
                            }
                        }
                    )
                )
            }
        }

        HorizontalDivider(
            color = Color.LightGray.copy(alpha = 0.3f),
            thickness = 0.5.dp
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.recentSearches) { historyItem ->
                SearchHistoryItem(
                    text = historyItem,
                    onClick = {
                        onChangedQuery(historyItem)
                        onSearch()
                    }
                )
            }
        }
    }
}
