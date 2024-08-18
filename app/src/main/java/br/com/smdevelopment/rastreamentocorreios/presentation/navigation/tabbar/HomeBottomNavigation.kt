package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.tabbar.BottomNavItem

@Composable
fun HomeBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Delivered,
        BottomNavItem.Home,
        BottomNavItem.Pending,
        BottomNavItem.Price
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.Black
    ) {
        val navBackStack by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStack?.destination?.route

        items.forEach { page ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = page.icon), contentDescription = page.title) },
                label = { Text(text = page.title, fontSize = 9.sp) },
                selectedContentColor = colorResource(id = R.color.primary_color),
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == page.route,
                onClick = {
                    navController.navigate(page.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}