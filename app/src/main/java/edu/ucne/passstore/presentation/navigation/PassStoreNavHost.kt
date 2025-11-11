package edu.ucne.passstore.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.passstore.presentation.home.HomeScreen
import edu.ucne.passstore.presentation.settings.SettingScreen
import edu.ucne.passstore.presentation.subcuenta.SubcuentaScreen
import edu.ucne.passstore.presentation.subcuenta.ViewSubcuentaScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                goViewSubcuentaScreen = { cuentaId ->
                    navHostController.navigate(Screen.ViewSubcuentaScreen(cuentaId))
                },
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
        composable<Screen.ViewSubcuentaScreen> {id ->
            val cuentaId = id.toRoute<Screen.ViewSubcuentaScreen>().cuentaId

            ViewSubcuentaScreen(
                cuentaId = cuentaId,
                context = context,
                goBack = {
                    navHostController.navigate(Screen.HomeScreen)
                }
            )
        }
    }
}