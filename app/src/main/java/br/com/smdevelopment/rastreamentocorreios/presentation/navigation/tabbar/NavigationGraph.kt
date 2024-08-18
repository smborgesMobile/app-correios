package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.sborges.price.presentation.screens.PriceHomeScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(5)) },
        exitTransition = { fadeOut(animationSpec = tween(5)) }
    ) {
        composable(BottomNavItem.Delivered.route) {
            DeliveredScreen()
        }
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Pending.route) {
            PendingScreen()
        }
        composable(NavDrawerItem.About.route) {
            AboutScreen()
        }
        composable(BottomNavItem.Price.route) {
            PriceHomeScreen()
        }
    }
}