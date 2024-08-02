package presentation.screens.home.state

import model.TaskModel

data class HomeStateHolder(
    var isLoading : Boolean = false,
    var data : List<TaskModel>? = emptyList(),
    var error : String = ""
)