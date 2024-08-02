import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayoutState
import moe.tlaster.precompose.PreComposeApp
import org.koin.compose.KoinContext
import presentation.screens.home.HomeScreen


@Composable
fun App() {
    KoinContext {
        PreComposeApp{
            MaterialTheme {
                HomeScreen()
            }
        }
    }
}