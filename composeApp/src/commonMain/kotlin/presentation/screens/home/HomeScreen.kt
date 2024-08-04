package presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.TaskModel
import moe.tlaster.precompose.navigation.Navigator
import navigation.Route
import presentation.screens.home.component.TaskCard
import presentation.screens.home.state.HomeScreenViewModel

@Composable
fun HomeScreen(navigator: Navigator, viewModel: HomeScreenViewModel) {
    LaunchedEffect(true) {
        viewModel.getAllTask()
    }
    val uiState by viewModel.uiState.collectAsState()

    //viewModel.insertTaskData(data)
    //settingsRepository.setUserEmail("gaddarkumar7447@gmail.com")
    //settingsRepository.setUserPassword("123456")


    Scaffold(
        topBar = {
            Text(
                text = "My Task",
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                modifier = Modifier.padding(20.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigator.navigate(route = Route.Home.EditScreen)
                viewModel.clearTask()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Button",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    LoadingView()
                }

                uiState.error.isNotEmpty() -> {
                    ErrorView(uiState.error)
                }

                uiState.data.isNullOrEmpty() -> {
                    EmptyView()
                }

                else -> {
                    TaskListView(uiState.data!!, viewModel, navigator)
                }
            }
        }
    }
}


@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(error: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = error, fontFamily = FontFamily.Serif)
    }
}

@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Please create your Task", fontFamily = FontFamily.Serif)
    }
}

@Composable
fun TaskListView(tasks: List<TaskModel>, viewModel: HomeScreenViewModel, navigator: Navigator) {
    val sortedTasks = tasks.sortedByDescending { it.created }
    val pendingTasks = sortedTasks.filter { it.status == "Pending" }
    val completedTasks = sortedTasks.filter { it.status == "Completed" }

    LazyColumn {
        item {
            Text(
                text = "Pending",
                fontFamily = FontFamily.Serif,
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 20.dp, end = 20.dp, start = 20.dp),
                color = Color.Red
            )
        }

        if (pendingTasks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No pending tasks",
                        fontSize = 17.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        } else {
            items(pendingTasks) { taskModel ->
                TaskCard(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    taskModelData = taskModel,
                    viewModel = viewModel,
                    navigator = navigator
                )
            }
        }

        item {
            Text(
                text = "Completed",
                fontFamily = FontFamily.Serif,
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 20.dp, end = 20.dp, start = 20.dp),
                color = Color.Green
            )
        }

        if (completedTasks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No completed tasks",
                        fontSize = 17.sp,
                        fontFamily = FontFamily.Serif,
                    )
                }

            }
        } else {
            items(completedTasks) { taskModel ->
                TaskCard(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    taskModelData = taskModel,
                    viewModel = viewModel,
                    navigator = navigator
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(bottom = 80.dp))
        }
    }
}
