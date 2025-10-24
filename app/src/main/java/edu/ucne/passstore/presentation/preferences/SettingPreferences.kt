package edu.ucne.passstore.presentation.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "settings")
class SettingPreferences @Inject constructor(
    private val context: Context
){
    companion object{
        val PIN_CODE = stringPreferencesKey("pin_code")
        const val DEFAULT_CODE = "123456"
    }

    suspend fun initializeDefaultPin() {
        context.dataStore.edit { pref ->
            if(!pref.contains(PIN_CODE)){
                pref[PIN_CODE] = DEFAULT_CODE
            }
        }
    }

    suspend fun getPinCode(): String{
        val preferences = context.dataStore.data.first()
        return preferences[PIN_CODE] ?: ""
    }

    suspend fun setPinCode(pinCode: String){
        context.dataStore.edit { preferences ->
            preferences[PIN_CODE] = pinCode
        }
    }
}