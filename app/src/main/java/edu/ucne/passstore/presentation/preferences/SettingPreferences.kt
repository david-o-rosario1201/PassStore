package edu.ucne.passstore.presentation.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        val BIOMETRIC_AUTH = booleanPreferencesKey("biometric_auth")
        const val DEFAULT_CODE = "123456"
    }

    suspend fun initializeDefaultPin() {
        context.dataStore.edit { pref ->
            if(!pref.contains(PIN_CODE)){
                pref[PIN_CODE] = DEFAULT_CODE
                pref[BIOMETRIC_AUTH] = false
            }
        }
    }

    suspend fun getPinCode(): String{
        val preferences = context.dataStore.data.first()
        return preferences[PIN_CODE] ?: ""
    }

    suspend fun getBiometricAuth(): Boolean{
        val preferences = context.dataStore.data.first()
        return preferences[BIOMETRIC_AUTH] ?: false
    }

    suspend fun setPinCode(pinCode: String){
        context.dataStore.edit { preferences ->
            preferences[PIN_CODE] = pinCode
        }
    }

    suspend fun setBiometricAuth(biometricAuth: Boolean){
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_AUTH] = biometricAuth
        }
    }
}