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
import androidx.compose.material3.SnackbarDuration
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
import br.com.coupledev.todocompose.ui.screens.list.content.ListContent
import br.com.coupledev.todocompose.ui.states.SearchAppBarState
import br.com.coupledev.todocompose.ui.theme.backgroundColor
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun ListScreen(
    action: ToDoAction,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState = sharedViewModel.searchAppBarSate
    val searchTextState: String = sharedViewModel.searchTextState
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackbarHostState = snackbarHostState,
        onComplete = { sharedViewModel.updateAction(newAction = it) },
        onUndoClicked = { sharedViewModel.updateAction(newAction = it) },
        taskTitle = sharedViewModel.title,
        action = action
    )

    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseAction(action = action)
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
            lowPriorityTasks = lowPriorityTasks,
            highPriorityTasks = highPriorityTasks,
            sortState = sortState,
            searchedTasks = searchedTasks,
            searchAppBarState = searchAppBarState,
            onSwipeToDelete = { action, task ->
                sharedViewModel.updateAction(newAction = action)
                sharedViewModel.updateTask(selectedTask = task)
                snackbarHostState.currentSnackbarData?.dismiss()
            },
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
    onUndoClicked: (action: ToDoAction) -> Unit,
    onComplete: (action: ToDoAction) -> Unit,
    taskTitle: String,
    action: ToDoAction
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = action) {
        if (action != ToDoAction.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackbarHostState.showSnackbar(
                    message = setSnackBarMessage(
                        context = context, action = action, taskTitle = taskTitle
                    ),
                    actionLabel = setActionLabel(context = context, action = action),
                    duration = SnackbarDuration.Short
                )
                undoDeleteTask(
                    action = action,
                    snackbarResult = snackBarResult,
                    onUndoClicked = onUndoClicked,
                )
            }
            onComplete(ToDoAction.NO_ACTION)
        }
    }
}

private fun setSnackBarMessage(context: Context, action: ToDoAction, taskTitle: String): String {
    return when (action) {
        ToDoAction.DELETE_ALL ->
            context.getString(R.string.all_tasks_deleted)

        else ->
            "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(context: Context, action: ToDoAction): String {
    return when (action.name) {
        ToDoAction.DELETE.toString() ->
            context.getString(R.string.undo)

        else ->
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