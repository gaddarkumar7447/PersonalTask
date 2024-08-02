package presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.TaskModel
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.stateholder.LocalStateHolder
import org.koin.compose.getKoin
import org.koin.compose.getKoinScope
import org.koin.compose.koinInject
import presentation.screens.home.state.HomeScreenViewModel
import sharedPref.SettingsRepository

@Composable
fun HomeScreen(

) {
    val viewModel = koinViewModel(HomeScreenViewModel::class)
    val uiState = viewModel.uiState.collectAsState()
    val settingsRepository : SettingsRepository = SettingsRepository()

//    val data = TaskModel(
//        id = 1,
//        title = "Work",
//        content = "this work has done",
//        created = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) ,
//        status = "pending"
//    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                //viewModel.insertTaskData(data)
                //settingsRepository.setUserEmail("gaddarkumar7447@gmail.com")
                //settingsRepository.setUserPassword("123456")
            }) {
                Text("click button")
            }


            settingsRepository.getUserEmail()?.let { Text(text = it) }
            if (settingsRepository.getUserPassword() != null){
                Text(text = settingsRepository.getUserPassword()!!)
            }else{
                Text(text = "Not entered")
            }
        }
    }
}