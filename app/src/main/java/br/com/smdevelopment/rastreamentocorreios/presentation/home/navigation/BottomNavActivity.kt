package br.com.smdevelopment.rastreamentocorreios.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.smdevelopment.rastreamentocorreios.presentation.home.screens.CarScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.home.screens.HomeScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.home.screens.StarScreen
import br.com.smdevelopment.rastreamentocorreios.presentation.home.tabbar.BottomNavItem

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Car.route) {
            CarScreen()
        }
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Star.route) {
            StarScreen()
        }
    }
}