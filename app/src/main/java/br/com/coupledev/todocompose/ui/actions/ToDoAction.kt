package br.com.coupledev.todocompose.ui.actions

enum class ToDoAction(val action: String) {
    ADD("ADD"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    DELETE_ALL("DELETE_ALL"),
    UNDO("UNDO"),
    NO_ACTION("NO_ACTION"),
}

fun String?.toAction(): ToDoAction {
    return if (this == null) ToDoAction.NO_ACTION else ToDoAction.valueOf(this)
}