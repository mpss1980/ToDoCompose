package br.com.coupledev.todocompose.ui.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.data.models.Priority
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.screens.task.appbar.TaskAppBar
import br.com.coupledev.todocompose.ui.viewmodels.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (action: ToDoAction) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == ToDoAction.NO_ACTION) {
                        navigateToListScreen(action)
                        return@TaskAppBar
                    }
                    if (sharedViewModel.validateFields()) {
                        navigateToListScreen(action)
                        return@TaskAppBar
                    }

                    Toast.makeText(
                        context, context.getString(R.string.fields_empty), Toast.LENGTH_SHORT
                    ).show()
                },
            )
        },
        content = { innerPadding ->
            TaskContent(
                modifier = Modifier.padding(innerPadding),
                title = title,
                onTitleChanged = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChanged = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )

}