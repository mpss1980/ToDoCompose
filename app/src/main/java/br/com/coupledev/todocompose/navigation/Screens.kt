package br.com.coupledev.todocompose.navigation

import androidx.navigation.NavHostController
import br.com.coupledev.todocompose.util.Action
import br.com.coupledev.todocompose.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
}