package br.com.coupledev.todocompose.ui.screens.task.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.ui.actions.ToDoAction

@ExperimentalMaterial3Api
@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigateToListScreen: (action: ToDoAction) -> Unit
) {
    if (selectedTask == null) {
        return NewTaskAppBar(
            navigateToListScreen = navigateToListScreen
        )
    }
    ExistingTaskAppBar(
        selectedTask = selectedTask,
        navigateToListScreen = navigateToListScreen
    )
}