package br.com.coupledev.todocompose.ui.actions

enum class ToDoAction() {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION,
}

fun String?.toAction(): ToDoAction {
    return if (this.isNullOrEmpty()) ToDoAction.NO_ACTION else ToDoAction.valueOf(this)
}