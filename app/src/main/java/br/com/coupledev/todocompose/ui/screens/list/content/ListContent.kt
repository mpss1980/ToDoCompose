package br.com.coupledev.todocompose.ui.screens.list.content

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.coupledev.todocompose.data.models.Priority
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.states.RequestState
import br.com.coupledev.todocompose.ui.states.SearchAppBarState

@ExperimentalMaterial3Api
@Composable
fun ListContent(
    modifier: Modifier,
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (ToDoAction, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    if (sortState !is RequestState.Success) return

    val tasks = when {
        searchAppBarState == SearchAppBarState.TRIGGERED
                && searchedTasks is RequestState.Success -> searchedTasks.data

        sortState.data == Priority.NONE
                && allTasks is RequestState.Success -> allTasks.data

        sortState.data == Priority.LOW -> lowPriorityTasks
        sortState.data == Priority.HIGH -> highPriorityTasks
        else -> null
    }

    if (tasks == null) {
        return
    }

    HandleListContent(
        modifier = modifier,
        tasks = tasks,
        onSwipeToDelete = onSwipeToDelete,
        navigateToTaskScreen = navigateToTaskScreen
    )
}

@ExperimentalMaterial3Api
@Composable
fun HandleListContent(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (ToDoAction, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyListContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}