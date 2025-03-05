package br.com.coupledev.todocompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.ui.screens.list.appbar.ListAppBar
import br.com.coupledev.todocompose.ui.states.SearchAppBarState
import br.com.coupledev.todocompose.ui.theme.backgroundColor
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel

@ExperimentalMaterial3Api
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarSate
    val searchTextState: String by sharedViewModel.searchTextState

    sharedViewModel.handleDatabaseAction(action = action)

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    Scaffold(
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
            )
        },
        content = { innerPadding ->
            ListContent(
                tasks = allTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                modifier = Modifier.padding(innerPadding)
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        containerColor = MaterialTheme.colorScheme.backgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}