package br.com.coupledev.todocompose.data.models

import androidx.compose.ui.graphics.Color
import br.com.coupledev.todocompose.ui.theme.HighPriorityColor
import br.com.coupledev.todocompose.ui.theme.LowPriorityColor
import br.com.coupledev.todocompose.ui.theme.MediumPriorityColor
import br.com.coupledev.todocompose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
