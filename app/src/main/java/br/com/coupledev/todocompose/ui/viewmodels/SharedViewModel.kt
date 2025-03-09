package br.com.coupledev.todocompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.coupledev.todocompose.data.models.Priority
import br.com.coupledev.todocompose.data.models.ToDoTask
import br.com.coupledev.todocompose.data.repositories.DataStoreRepository
import br.com.coupledev.todocompose.data.repositories.ToDoRepository
import br.com.coupledev.todocompose.ui.actions.ToDoAction
import br.com.coupledev.todocompose.ui.states.RequestState
import br.com.coupledev.todocompose.ui.states.SearchAppBarState
import br.com.coupledev.todocompose.ui.theme.Constants.MAX_TITLE_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val action: MutableState<ToDoAction> = mutableStateOf(ToDoAction.NO_ACTION)

    val id: MutableState<Int> = mutableIntStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarSate: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            toDoRepository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    fun searchTasks(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarSate.value = SearchAppBarState.TRIGGERED
    }

    fun updateTask(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
            return
        }

        id.value = 0
        title.value = ""
        description.value = ""
        priority.value = Priority.LOW
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

    fun handleDatabaseAction(action: ToDoAction) {
        when (action) {
            ToDoAction.ADD -> {
                addTask()
            }

            ToDoAction.UPDATE -> {
                updateTask()
            }

            ToDoAction.DELETE -> {
                deleteTask()
            }

            ToDoAction.DELETE_ALL -> {
                deleteAllTasks()
            }

            ToDoAction.UNDO -> {
                addTask()
            }

            ToDoAction.NO_ACTION -> {

            }
        }
        this.action.value = ToDoAction.NO_ACTION
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    val lowPriorityTasks: StateFlow<List<ToDoTask>> = toDoRepository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTasks: StateFlow<List<ToDoTask>> = toDoRepository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            toDoRepository.addTask(toDoTask)
        }
        searchAppBarSate.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            toDoRepository.updateTask(toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            toDoRepository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllTasks()
        }
    }
}