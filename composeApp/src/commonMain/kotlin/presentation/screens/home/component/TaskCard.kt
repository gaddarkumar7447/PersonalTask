package presentation.screens.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.TaskModel
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import navigation.Route
import presentation.screens.home.state.HomeScreenViewModel
import utils.convertDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    taskModelData: TaskModel,
    viewModel: HomeScreenViewModel,
    navigator: Navigator
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        onClick = {
            navigator.navigate(route = Route.Home.EditScreen)
            viewModel.selectTask(taskModelData)
        }
    ) {
        Box(modifier = Modifier.padding(5.dp)) {
            Row {
                Checkbox(
                    checked = taskModelData.status != "Pending",
                    onCheckedChange = { isChecked ->
                        val newStatus = if (isChecked) "Completed" else "Pending"
                        viewModel.insertTaskData(
                            TaskModel(
                                id = taskModelData.id,
                                title = taskModelData.title,
                                content = taskModelData.content,
                                created = taskModelData.created,
                                status = newStatus
                            )
                        )
                        coroutineScope.launch {
                            viewModel.getAllTask()
                        }

                    }
                )

                Column {
                    Text(
                        text = taskModelData.title,
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = taskModelData.content,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.End)
                    ) {
                        Text(
                            text = taskModelData.created.toString().convertDateTime(),
                            fontFamily = FontFamily.Serif,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}