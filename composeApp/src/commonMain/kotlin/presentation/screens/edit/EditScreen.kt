package presentation.screens.edit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.TaskModel
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import personaltask.composeapp.generated.resources.Res
import presentation.screens.home.state.HomeScreenViewModel
import utils.ResultStatus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(viewModel: HomeScreenViewModel, navigator: Navigator) {
    val selectedTask by viewModel.selectedTask.collectAsState()
    val focus = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    var dataTask by remember { mutableStateOf<TaskModel?>(null) }
    selectedTask.let {
        dataTask = it
    }

    var title by remember { mutableStateOf(if (dataTask != null) dataTask!!.title else "") }
    var description by remember { mutableStateOf(if (dataTask != null) dataTask!!.content else "") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (dataTask == null) {
                        Text(text = "Write New Task", fontFamily = FontFamily.Serif, fontSize = 18.sp)
                    }else{
                        Text(text = "Update Task", fontFamily = FontFamily.Serif, fontSize = 18.sp)
                    }
                },
                actions = {
                    if (dataTask != null){
                        IconButton(onClick = {
                            viewModel.deleteTaskById(id = dataTask!!.id!!)
                            navigator.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }

                    IconButton(onClick = {
                        viewModel.insertTaskData(
                            taskModel = TaskModel(
                                id = if (dataTask == null) Clock.System.now().toEpochMilliseconds() else dataTask!!.id,
                                title = title,
                                content = description,
                                created = if (dataTask == null) Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) else dataTask!!.created,
                                status = if (dataTask == null) "Pending" else dataTask!!.status
                            )
                        )
                        navigator.popBackStack()
                    }, enabled = isValidTitleAndDescription(title, description)) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            LazyColumn {
                item {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = { newTitle ->
                            title = newTitle
                        },
                        placeholder = {
                            Text(
                                text = "title",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700,
                                color = Color.Gray,
                                fontFamily = FontFamily.Serif
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif
                        ),
                        maxLines = Int.MAX_VALUE,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focus.moveFocus(FocusDirection.Down)
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = description,
                        onValueChange = { newDescription ->
                            description = newDescription
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequester),

                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = false,
                        enabled = true,
                        interactionSource = interactionSource,
                        placeholder = {
                            Text(
                                text = "Description",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                color = Color.Gray,
                                fontFamily = FontFamily.Serif
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                }
            }
        }
    }
}

fun isValidTitleAndDescription(title: String, description: String): Boolean {
    if (title.isEmpty()){
        return false
    }else if (description.isEmpty()){
        return false
    }
    return true
}
