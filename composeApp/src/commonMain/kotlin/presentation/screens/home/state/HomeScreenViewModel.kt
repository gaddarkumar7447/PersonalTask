package presentation.screens.home.state

import domain.user_cases.DeleteTaskUseCase
import domain.user_cases.GetAllTaskUseCase
import domain.user_cases.InsertTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import model.TaskModel
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import utils.ResultStatus

class HomeScreenViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTaskUseCase: GetAllTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeStateHolder())
    val uiState : StateFlow<HomeStateHolder> = _uiState.asStateFlow()

    init {
        getAllTask()
    }

    private fun getAllTask() = getAllTaskUseCase().onEach {res ->
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
                    HomeStateHolder(
                        data = res.data
                    )
                }
            }

            is ResultStatus.Error -> {
                _uiState.update {
                    HomeStateHolder(
                        error = res.messsage.toString()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    fun insertTaskData(taskModel: TaskModel) = insertTaskUseCase(taskModel).launchIn(viewModelScope)

    fun deleteTaskById(id : Long) = deleteTaskUseCase(id).launchIn(viewModelScope)

}