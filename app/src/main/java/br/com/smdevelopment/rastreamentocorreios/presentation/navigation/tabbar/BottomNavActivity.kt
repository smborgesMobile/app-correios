package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu.NavDrawerItem
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.about.AboutScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered.DeliveredScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.HomeScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.pending.PendingScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.tabbar.BottomNavItem

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Delivered.route) {
            DeliveredScreen()
        }
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Pending.route) {
            PendingScreen()
        }
        composable(NavDrawerItem.Settings.route) {
            AboutScreen()
        }
        composable(NavDrawerItem.About.route) {
            AboutScreen()
        }
    }
}