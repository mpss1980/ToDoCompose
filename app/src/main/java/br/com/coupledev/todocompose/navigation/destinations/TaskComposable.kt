package br.com.coupledev.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.navigation.NavigationConstants.TASK_ARGUMENT_KEY
import br.com.coupledev.todocompose.navigation.NavigationConstants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (ToDoAction) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}