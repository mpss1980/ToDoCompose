package br.com.coupledev.todocompose.navigation

import androidx.navigation.NavHostController
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.navigation.NavigationConstants.LIST_SCREEN
import br.com.coupledev.todocompose.navigation.NavigationConstants.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${ToDoAction.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val list: (ToDoAction) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
}