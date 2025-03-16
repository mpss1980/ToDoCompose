package br.com.coupledev.todocompose.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import br.com.coupledev.todocompose.navigation.NavigationConstants.SPLASH_SCREEN
import br.com.coupledev.todocompose.navigation.destinations.listComposable
import br.com.coupledev.todocompose.navigation.destinations.taskComposable
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel
import br.com.coupledev.todocompose.navigation.destinations.splashComposable

@ExperimentalMaterial3Api
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
    }
}