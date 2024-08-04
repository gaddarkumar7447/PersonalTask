package presentation.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import navigation.Route
import sharedPref.SettingsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    navigator: Navigator,
    settingsRepository: SettingsRepository
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "LogIn",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn {
                item {
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        label = {
                            Text(text = "Enter email", fontFamily = FontFamily.Serif)
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        keyboardActions = KeyboardActions(onDone = null),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        label = {
                            Text(text = "Enter Password", fontFamily = FontFamily.Serif)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password"
                            )
                        },
                        keyboardActions = KeyboardActions(onDone = null),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AnimatedVisibility(visible = showError) {
                        Text(
                            text = errorMessage,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.Red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "Don't have account? ", fontFamily = FontFamily.Serif)

                        Text(
                            text = "Signup",
                            fontFamily = FontFamily.Serif,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .padding(start = 2.dp, end = 2.dp)
                                .clip(shape = RoundedCornerShape(4.dp))
                                .clickable {
                                    navigator.navigate(route = Route.Auth.Signup)
                                }
                        )
                    }


                    Spacer(modifier = Modifier.height(22.dp))

                    FilledTonalButton(
                        onClick = {
                            val savedEmail = settingsRepository.getUserEmail()
                            val savedPassword = settingsRepository.getUserPassword()
                            when {
                                savedEmail.isNullOrEmpty() && savedPassword.isNullOrEmpty() -> {
                                    showError = true
                                    errorMessage = "No user exists, please sign up."
                                }
                                savedEmail == email && savedPassword == password -> {
                                    navigator.navigate(route = Route.Home.HomeRoute,
                                        options = NavOptions(
                                            popUpTo = PopUpTo(
                                                route = Route.Auth.AuthRoute,
                                                inclusive = true
                                            )
                                        )
                                    )
                                }
                                else -> {
                                    showError = true
                                    errorMessage = "Please enter valid credentials."
                                }
                            }

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp),
                        enabled = isValid(email, password)
                    ) {
                        Text(text = "LogIn", fontFamily = FontFamily.Serif)
                    }
                }
            }
        }
    }
}