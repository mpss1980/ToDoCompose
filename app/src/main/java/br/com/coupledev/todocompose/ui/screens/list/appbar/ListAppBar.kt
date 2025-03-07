package br.com.coupledev.todocompose.ui.screens.list.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import br.com.coupledev.todocompose.ui.states.SearchAppBarState
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel

@ExperimentalMaterial3Api
@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarSate.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                onDeleteClicked = {}
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                    sharedViewModel.searchTextState.value = newText
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarSate.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    sharedViewModel.searchTasks(searchQuery = it)
                }
            )
        }
    }
}