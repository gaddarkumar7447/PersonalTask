package sharedPref

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsRepository : KoinComponent {
    private val setting : Settings by inject()

    fun setUserEmail(email : String){
        setting["email"] = email
    }
    fun setUserPassword(password : String){
        setting["password"] = password
    }


    fun getUserEmail() : String?{
        return setting.getStringOrNull("email")
    }

    fun getUserPassword() : String?{
        return setting.getStringOrNull("password")
    }
}