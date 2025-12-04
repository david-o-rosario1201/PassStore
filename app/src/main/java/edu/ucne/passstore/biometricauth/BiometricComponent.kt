package edu.ucne.passstore.biometricauth

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import edu.ucne.passstore.presentation.settings.SettingUiEvent

@Composable
fun BiometricComponent(
    promptManager: BiometricPromptManager,
    onEvent: (SettingUiEvent) -> Unit
){
    val biometricResult by promptManager.promptResults.collectAsState(
        initial = null
    )
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )
    LaunchedEffect(biometricResult){
        if(biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet){
            if(Build.VERSION.SDK_INT >= 30){
                val enrollInt = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollInt)
            }
        }
    }

    biometricResult?.let { result ->
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                onEvent(SettingUiEvent.BiometricAuthSuccess(false))
            }

            BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                onEvent(SettingUiEvent.BiometricAuthSuccess(false))
            }

            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                // No tiene biometría configurada → NO es éxito
                onEvent(SettingUiEvent.BiometricAuthSuccess(false))
            }

            BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                onEvent(SettingUiEvent.BiometricAuthSuccess(true))
            }

            BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                onEvent(SettingUiEvent.BiometricAuthSuccess(false))
            }

            BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                onEvent(SettingUiEvent.BiometricAuthSuccess(false))
            }
        }
    }
}