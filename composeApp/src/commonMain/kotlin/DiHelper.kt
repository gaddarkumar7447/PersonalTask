import di.dataModule
import di.platformModule
import domain.module.domainModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import presentation.di.presentationModule
import sharedPref.di.settingModule

fun initKoin(koinApp : ((KoinApplication) -> Unit) ?= null){
    startKoin {
        koinApp?.invoke(this)
        modules(platformModule, dataModule, presentationModule, domainModule, settingModule)
    }
}