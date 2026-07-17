package com.guitarproacademy.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.guitarproacademy.app.navigation.Destination

data class BottomNavItem(
    val destination: Destination,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Destination.Home, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(Destination.Learn, "Learn", Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
    BottomNavItem(Destination.Practice, "Practice", Icons.Filled.MusicNote, Icons.Outlined.MusicNote),
    BottomNavItem(Destination.Progress, "Progress", Icons.Filled.TrendingUp, Icons.Outlined.TrendingUp),
    BottomNavItem(Destination.Profile, "Profile", Icons.Filled.Person, Icons.Outlined.Person)
)

@Composable
fun GuitarBottomNavBar(currentRoute: String?, onNavigate: (Destination) -> Unit) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.destination.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
