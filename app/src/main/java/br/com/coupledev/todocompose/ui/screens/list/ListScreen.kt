package br.com.coupledev.todocompose.ui.screens.list

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.screens.list.appbar.ListAppBar
import br.com.coupledev.todocompose.ui.states.SearchAppBarState
import br.com.coupledev.todocompose.ui.theme.backgroundColor
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarSate
    val searchTextState: String by sharedViewModel.searchTextState

    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackbarHostState = snackbarHostState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseAction(action = action) },
        onUndoClicked = { sharedViewModel.action.value = it },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    Scaffold(topBar = {
        ListAppBar(
            sharedViewModel = sharedViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState,
        )
    }, content = { innerPadding ->
        ListContent(
            modifier = Modifier.padding(innerPadding),
            allTasks = allTasks,
            searchedTasks = searchedTasks,
            searchAppBarState = searchAppBarState,
            navigateToTaskScreen = navigateToTaskScreen,
        )
    }, floatingActionButton = {
        ListFab(onFabClicked = navigateToTaskScreen)
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState
        )
    })
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        }, containerColor = MaterialTheme.colorScheme.backgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun DisplaySnackBar(
    snackbarHostState: SnackbarHostState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (action: ToDoAction) -> Unit,
    taskTitle: String,
    action: ToDoAction
) {
    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = action) {
        if (action != ToDoAction.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackbarHostState.showSnackbar(
                    message = "${action.name}: $taskTitle",
                    actionLabel = setActionLabel(context = context, action = action),
                )
                undoDeleteTask(
                    action = action,
                    snackbarResult = snackBarResult,
                    onUndoClicked = onUndoClicked,
                )
            }
        }
    }
}

private fun setActionLabel(context: Context, action: ToDoAction): String {
    return if (action.name == ToDoAction.DELETE.action) {
        context.getString(R.string.undo)
    } else {
        context.getString(R.string.ok)
    }
}

private fun undoDeleteTask(
    action: ToDoAction, snackbarResult: SnackbarResult, onUndoClicked: (action: ToDoAction) -> Unit
) {
    if (snackbarResult == SnackbarResult.ActionPerformed && action == ToDoAction.DELETE) {
        onUndoClicked(ToDoAction.UNDO)
    }
}