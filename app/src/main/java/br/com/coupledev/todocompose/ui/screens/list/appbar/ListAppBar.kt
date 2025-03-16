package br.com.coupledev.todocompose.ui.screens.list.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import br.com.coupledev.todocompose.ui.actions.ToDoAction
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
                    sharedViewModel.updateSearchAppBarState(newState = SearchAppBarState.OPENED)
                },
                onSortClicked = { sharedViewModel.persistSortState(it) },
                onDeleteAllClicked = {
                    sharedViewModel.updateAction(newAction = ToDoAction.DELETE_ALL)
                }
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                    sharedViewModel.updateSearchTextState(newText =  newText)
                },
                onCloseClicked = {
                    sharedViewModel.updateSearchAppBarState(newState = SearchAppBarState.CLOSED)
                    sharedViewModel.updateSearchTextState(newText = "")
                },
                onSearchClicked = {
                    sharedViewModel.searchTasks(searchQuery = it)
                }
            )
        }
    }
}