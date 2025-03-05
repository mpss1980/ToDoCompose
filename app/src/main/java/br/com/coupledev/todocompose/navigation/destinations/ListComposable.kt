package br.com.coupledev.todocompose.navigation.destinations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.coupledev.todocompose.ui.screens.list.ListScreen
import br.com.coupledev.todocompose.navigation.NavigationConstants.LIST_ARGUMENT_KEY
import br.com.coupledev.todocompose.navigation.NavigationConstants.LIST_SCREEN
import br.com.coupledev.todocompose.ui.actions.toAction
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel

@ExperimentalMaterial3Api
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}