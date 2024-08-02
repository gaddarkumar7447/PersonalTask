package org.example.personaltask

import App
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import initKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            it.modules(module {
                single { this@BaseApplication.applicationContext }
            })

        }
    }
}
