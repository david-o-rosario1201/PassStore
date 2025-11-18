package edu.ucne.passstore.presentation.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.userDataStore by preferencesDataStore(name = "user")
class UserPreferences  @Inject constructor(
    private val context: Context
){
    companion object{
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_DATE_REGISTER = stringPreferencesKey("user_date_register")
    }

    suspend fun getUserInfo(): UserInfo{
        val preferences = context.userDataStore.data.first()
        return UserInfo(
            userName = preferences[USER_NAME] ?: "",
            userDateRegister = preferences[USER_DATE_REGISTER] ?: ""
        )
    }

    suspend fun setUserInfo(userName: String){
        context.userDataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }
}

data class UserInfo(
    val userName: String,
    val userDateRegister: String
)