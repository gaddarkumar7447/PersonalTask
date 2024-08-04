package presentation.screens.home.state

import domain.user_cases.DeleteTaskUseCase
import domain.user_cases.GetAllTaskUseCase
import domain.user_cases.GetTaskByIdUseCase
import domain.user_cases.InsertTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.TaskModel
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import utils.ResultStatus

class HomeScreenViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val getTaskByIdUseCase : GetTaskByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeStateHolder())
    val uiState : StateFlow<HomeStateHolder> = _uiState.asStateFlow()

    private val _task = MutableStateFlow<ResultStatus<TaskModel>>(ResultStatus.Loading())
    val task: StateFlow<ResultStatus<TaskModel>> = _task.asStateFlow()

    fun getAllTask() = getAllTaskUseCase().onEach {res ->
        when (res) {
            is ResultStatus.Loading -> {
                _uiState.update {
                    HomeStateHolder(
                        isLoading = true
                    )
                }
            }

            is ResultStatus.Success -> {
                _uiState.update {
                    it.copy(
                        data = res.data,
                        isLoading = false
                    )
                }
            }

            is ResultStatus.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = res.messsage.toString()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    fun insertTaskData(taskModel: TaskModel) = insertTaskUseCase(taskModel).launchIn(viewModelScope)

    fun deleteTaskById(id : Long) = deleteTaskUseCase(id).launchIn(viewModelScope)

    fun getTaskById(id : Long) = getTaskByIdUseCase(id).onEach {
        _task.value = it
    }.launchIn(viewModelScope)


    private val _selectedTask = MutableStateFlow<TaskModel?>(null)
    val selectedTask: StateFlow<TaskModel?> = _selectedTask.asStateFlow()

    fun selectTask(task: TaskModel) {
        _selectedTask.value = task
    }

    fun clearTask() {
        _selectedTask.value = null
    }

}