package br.com.coupledev.todocompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.coupledev.todocompose.R
import br.com.coupledev.todocompose.ui.theme.LARGE_PADDING
import br.com.coupledev.todocompose.ui.theme.MEDIUM_PADDING
import br.com.coupledev.todocompose.ui.theme.SMALL_PADDING
import br.com.coupledev.todocompose.ui.theme.backgroundColor
import br.com.coupledev.todocompose.ui.theme.borderButtonOverlay
import br.com.coupledev.todocompose.ui.theme.contentColor
import br.com.coupledev.todocompose.ui.theme.overlayBackgroundColor

@ExperimentalMaterial3Api
@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
) {
    if (!openDialog) return

    BasicAlertDialog(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.overlayBackgroundColor,
                shape = MaterialTheme.shapes.medium
            ),
        content = {
            Column(
                modifier = Modifier.padding(all = LARGE_PADDING),
            ) {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(MEDIUM_PADDING))
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
                Spacer(modifier = Modifier.padding(MEDIUM_PADDING))
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors().copy(
                            contentColor = MaterialTheme.colorScheme.borderButtonOverlay,
                        ),
                        border = BorderStroke(
                            width = ButtonDefaults.outlinedButtonBorder.width,
                            color = MaterialTheme.colorScheme.borderButtonOverlay,
                        ),
                        onClick = {
                            closeDialog()
                        }
                    ) {
                        Text(text = stringResource(R.string.no))
                    }
                    Spacer(modifier = Modifier.size(SMALL_PADDING))
                    Button(
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.backgroundColor,
                            contentColor = MaterialTheme.colorScheme.contentColor,
                        ),
                        onClick = {
                            onYesClicked()
                            closeDialog()
                        }
                    ) {
                        Text(text = stringResource(R.string.yes))
                    }
                }
            }
        },
        onDismissRequest = { closeDialog() }
    )
}

@ExperimentalMaterial3Api
@PreviewLightDark
@Composable
private fun DisplayAlertDialogPreview() {
    DisplayAlertDialog(
        title = "Title",
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        openDialog = true,
        closeDialog = {},
        onYesClicked = {}
    )
}