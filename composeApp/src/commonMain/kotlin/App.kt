import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import navigation.NavigationRoute
import org.koin.compose.KoinContext
import presentation.screens.home.HomeScreen


@Composable
fun App() {
    KoinContext {
        PreComposeApp{
            MaterialTheme {
                NavigationRoute()
            }
        }
    }
}