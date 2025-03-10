package br.com.coupledev.todocompose.ui.screens.list.content

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.theme.Constants.ANIMATION_DELAY
import br.com.coupledev.todocompose.ui.theme.HighPriorityColor
import br.com.coupledev.todocompose.ui.theme.LARGEST_PADDING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalMaterial3Api
@Composable
fun DisplayTasks(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (ToDoAction, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskItemSwipeToDismiss(
                task = task,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterial3Api
@Composable
fun TaskItemSwipeToDismiss(
    task: ToDoTask,
    onSwipeToDelete: (ToDoAction, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    val isDismissed = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart
            && dismissState.progress > 0.2f
    val degrees by animateFloatAsState(
        if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0f else -45f
    )

    if (isDismissed) {
        val scope = rememberCoroutineScope()
        scope.launch(Dispatchers.IO) {
            delay(ANIMATION_DELAY)
            withContext(Dispatchers.Main) {
                onSwipeToDelete(ToDoAction.DELETE, task)
            }
        }
    }

    var itemAppeared by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        itemAppeared = true
    }

    AnimatedVisibility(
        visible = itemAppeared && !isDismissed,
        enter = expandVertically(
            animationSpec = tween(
                durationMillis = ANIMATION_DELAY.toInt()
            )
        ),
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = ANIMATION_DELAY.toInt()
            )
        ),
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { RedBackground(degrees = degrees) },
            enableDismissFromStartToEnd = false,
            enableDismissFromEndToStart = true,
            content = {
                TaskItem(
                    toDoTask = task,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        )
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_task_action),
            tint = Color.White,
        )
    }
}

@Preview
@Composable
private fun RedBackgroundPreview() {
    Column(modifier = Modifier.height(80.dp)) {
        RedBackground(degrees = 0f)
    }
}