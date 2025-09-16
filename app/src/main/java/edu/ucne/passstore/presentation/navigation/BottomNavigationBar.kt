package edu.ucne.passstore.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController
){
    val items = remember { getBottomNavigationItems() }

    //Navegación backstack
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val barColor = MaterialTheme.colorScheme.surface

    NavigationBar(
        containerColor = barColor,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    ){
        items.forEachIndexed { index, item ->
            val isSelected = currentDestination == item.screen::class.qualifiedName

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navHostController.navigate(item.screen){
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navHostController.graph.startDestinationId){
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if(isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = if(isSelected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                },
                label = {
                    Text(
                       text = item.label,
                        color = if(isSelected)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    indicatorColor = MaterialTheme.colorScheme.primary
                ),
                alwaysShowLabel = true
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val screen: Screen
)

private fun getBottomNavigationItems(): List<BottomNavigationItem>{
    return listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            label = "Home",
            screen = Screen.HomeScreen
        ),
        BottomNavigationItem(
            title = "Nueva cuenta",
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
            label = "Nueva cuenta",
            screen = Screen.SubcuentaScreen
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            label = "Settings",
            screen = Screen.SettingScreen
        )
    )
}