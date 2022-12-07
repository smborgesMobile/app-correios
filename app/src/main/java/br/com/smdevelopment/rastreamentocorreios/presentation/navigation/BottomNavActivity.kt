package br.com.smdevelopment.rastreamentocorreios.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.car.CarScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.HomeScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.star.StarScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.tabbar.BottomNavItem

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Delivered.route) {
            CarScreen()
        }
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Pending.route) {
            StarScreen()
        }
    }
}