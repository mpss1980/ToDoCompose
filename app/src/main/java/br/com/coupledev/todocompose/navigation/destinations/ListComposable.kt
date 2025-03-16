package br.com.coupledev.todocompose.navigation.destinations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.coupledev.todocompose.ui.screens.list.ListScreen
import br.com.coupledev.todocompose.navigation.NavigationConstants.LIST_ARGUMENT_KEY
import br.com.coupledev.todocompose.navigation.NavigationConstants.LIST_SCREEN
import br.com.coupledev.todocompose.ui.actions.ToDoAction
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
        val actionFromArguments = navBackStackEntry.arguments
            ?.getString(LIST_ARGUMENT_KEY).toAction()

        val action = sharedViewModel.action
        var myAction by rememberSaveable { mutableStateOf(ToDoAction.NO_ACTION) }

        LaunchedEffect(key1 = myAction) {
            if (actionFromArguments != myAction) {
                myAction = actionFromArguments
                sharedViewModel.updateAction(newAction = actionFromArguments)
            }
        }

        ListScreen(
            action = action,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}