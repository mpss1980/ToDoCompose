package br.com.coupledev.todocompose.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.screens.task.appbar.TaskAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (action: ToDoAction) -> Unit
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = navigateToListScreen,
                selectedTask = selectedTask
            )
        },
        content = {

        }
    )

}