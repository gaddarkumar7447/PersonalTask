package navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.screens.auth.LogInScreen
import presentation.screens.auth.SignUpScreen
import presentation.screens.edit.EditScreen
import presentation.screens.home.HomeScreen
import presentation.screens.home.state.HomeScreenViewModel
import sharedPref.SettingsRepository

@Composable
fun NavigationRoute(

) {
    val navigator = rememberNavigator()
    val viewModel = koinViewModel(HomeScreenViewModel::class)
    val settingsRepository: SettingsRepository = SettingsRepository()


    Scaffold {
        NavHost(
            navigator = navigator,
            initialRoute = Route.Auth.AuthRoute
        ) {
            this.group(
                route = Route.Auth.AuthRoute,
                initialRoute = Route.Auth.Login
            ) {
                this.scene(route = Route.Auth.Login) {
                    LogInScreen(
                        navigator,
                        settingsRepository
                    )
                }

                this.scene(route = Route.Auth.Signup) {
                    SignUpScreen(
                        navigator,
                        settingsRepository
                    )
                }

            }

            this.group(
                route = Route.Home.HomeRoute,
                initialRoute = Route.Home.HomeScreen
            ) {
                this.scene(route = Route.Home.HomeScreen) {
                    HomeScreen(navigator, viewModel)
                }
                this.scene(route = Route.Home.EditScreen) {
                    //val taskModel : TaskModel? = it.path<TaskModel>(path = "taskModel")
                    EditScreen(
                        viewModel,
                        navigator
                    )
                }
            }
        }

    }
}