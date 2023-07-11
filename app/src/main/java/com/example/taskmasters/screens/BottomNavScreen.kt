package com.example.taskmasters.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector?,
    val page: Int?
) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Главная",
        icon = Icons.Default.Home,
        page = 0
    )

    object Calendar : BottomNavScreen(
        route = "calendar",
        title = "Календарь",
        icon = Icons.Default.LocationOn,
        page = 1
    )

    object Settings : BottomNavScreen(
        route = "settings",
        title = "Настройки",
        icon = Icons.Default.Settings,
        page = 2
    )

    object Table: BottomNavScreen(
        "table",
        "Table Screen",
        icon = null,
        page = null
    )
}

@Composable
fun BottomBar(navController: NavController) {
    Surface(
        elevation = 16.dp,
        color = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        BottomNavigation(
            backgroundColor = Color.White
        ) {
            val items = listOf(
                BottomNavScreen.Home,
                BottomNavScreen.Calendar,
                BottomNavScreen.Settings
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon!!, "bottomBarIcon") },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    label = { Text(text = screen.title) },
                    unselectedContentColor = if (currentDestination?.route != screen.route) {
                        Color.LightGray
                    } else {
                        Color.Black
                    },
                    modifier = if (currentDestination?.route == screen.route) {
                        Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Cyan)
                            .size(40.dp, 50.dp)
                    } else {
                        Modifier
                    }
                )
            }
        }
    }
}

@Composable
fun Space(height: Int) {
    val dp = height.dp
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun TopBar(screen: BottomNavScreen) {
    Surface(
        elevation = 10.dp,
        color = Color.White,
        shape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)
    ) {
        TopAppBar(
            backgroundColor = Color.White,
        ) {
            Text(text = screen.title, fontSize = 18.sp, modifier = Modifier.padding(start = 10.dp))

        }
    }
}

@Composable
fun TopBar(title: String) {
    Surface(
        elevation = 10.dp,
        color = Color.White,
        shape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)
    ) {
        TopAppBar(
            backgroundColor = Color.White,
        ) {
            Text(text = title, fontSize = 18.sp, modifier = Modifier.padding(start = 10.dp))
        }
    }
}
