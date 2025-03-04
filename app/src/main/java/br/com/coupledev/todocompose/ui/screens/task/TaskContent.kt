package br.com.coupledev.todocompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.data.models.Priority
import br.com.coupledev.todocompose.ui.components.PriorityDropDown
import br.com.coupledev.todocompose.ui.theme.LARGE_PADDING
import br.com.coupledev.todocompose.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    modifier: Modifier,
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = MEDIUM_PADDING),
            color = MaterialTheme.colorScheme.background

        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = MEDIUM_PADDING),
            color = MaterialTheme.colorScheme.background
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}

@PreviewLightDark
@Composable
private fun TaskContentPreview() {
    TaskContent(
        modifier = Modifier,
        title = "Title",
        onTitleChanged = {},
        description = "description...",
        onDescriptionChanged = {},
        priority = Priority.LOW,
    ) { }
}