package edu.ucne.passstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.passstore.presentation.navigation.PassStoreNavHost
import edu.ucne.passstore.ui.theme.PassStoreTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PassStoreTheme {
                val navHostController = rememberNavController()
                PassStoreNavHost(navHostController = navHostController)
            }
        }
    }
}