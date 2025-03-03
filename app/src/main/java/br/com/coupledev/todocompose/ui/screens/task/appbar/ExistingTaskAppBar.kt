package br.com.coupledev.todocompose.ui.screens.task.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.data.models.Priority
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.theme.backgroundColor
import br.com.coupledev.todocompose.ui.theme.contentColor

@ExperimentalMaterial3Api
@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (action: ToDoAction) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colorScheme.contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.backgroundColor
        ),
        actions = {
            DeleteAction(onDeleteClicked = navigateToListScreen)
            UpdateAction(onUpdateClicked = navigateToListScreen)
        }
    )
}

@Composable
fun CloseAction(
    onCloseClicked: (ToDoAction) -> Unit
) {
    IconButton(
        onClick = { onCloseClicked(ToDoAction.NO_ACTION) }
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close_icon),
            tint = MaterialTheme.colorScheme.contentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: (ToDoAction) -> Unit
) {
    IconButton(
        onClick = { onDeleteClicked(ToDoAction.DELETE) }
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_task_action),
            tint = MaterialTheme.colorScheme.contentColor
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (ToDoAction) -> Unit
) {
    IconButton(
        onClick = { onUpdateClicked(ToDoAction.UPDATE) }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_task),
            tint = MaterialTheme.colorScheme.contentColor
        )
    }
}


@ExperimentalMaterial3Api
@PreviewLightDark
@Composable
private fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        selectedTask = ToDoTask(
            id = 0,
            title = "Task Title",
            description = "Task Description",
            priority = Priority.LOW
        ),
        navigateToListScreen = {}
    )
}