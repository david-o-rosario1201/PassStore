package edu.ucne.passstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.passstore.presentation.home.HomeScreen
import edu.ucne.passstore.presentation.settings.SettingScreen
import edu.ucne.passstore.presentation.subcuenta.SubcuentaScreen

@Composable
fun PassStoreNavHost(
    navHostController: NavHostController
){
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            HomeScreen(
                context = context,
                navHostController = navHostController
            )
        }
        composable<Screen.SubcuentaScreen> {
            SubcuentaScreen(
                context = context,
                goHome = { navHostController.navigate(Screen.HomeScreen)}
            )
        }
        composable<Screen.SettingScreen> {
            SettingScreen(
                context = context,
                navHostController = navHostController
            )
        }
    }
}