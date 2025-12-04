package edu.ucne.passstore

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.passstore.biometricauth.BiometricPromptManager
import edu.ucne.passstore.presentation.navigation.PassStoreNavHost
import edu.ucne.passstore.ui.theme.PassStoreTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PassStoreTheme {
                val navHostController = rememberNavController()
                PassStoreNavHost(
                    promptManager = promptManager,
                    navHostController = navHostController
                )
            }
        }
    }
}