package br.com.coupledev.todocompose.navigation.destinations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.navigation.NavigationConstants.TASK_ARGUMENT_KEY
import br.com.coupledev.todocompose.navigation.NavigationConstants.TASK_SCREEN
import br.com.coupledev.todocompose.ui.screens.task.TaskScreen
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel

@ExperimentalMaterial3Api
fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (ToDoAction) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId = taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            sharedViewModel.updateTask(selectedTask = selectedTask)
        }

        TaskScreen(
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}