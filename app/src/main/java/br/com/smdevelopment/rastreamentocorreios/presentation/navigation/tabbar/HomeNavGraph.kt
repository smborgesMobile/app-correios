package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.smdevelopment.rastreamentocorreios.presentation.MainScreenView
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.login.LoginScreen

const val LOGIN_ROUTE = "login"
const val MAIN_ROUTE = "main_screen"

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LOGIN_ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(5)) },
        exitTransition = { fadeOut(animationSpec = tween(5)) }
    ) {

        composable(LOGIN_ROUTE) {
            LoginScreen(navController = navController)
        }
        composable("main_screen") {
            MainScreenView(homeNavController = navController)
        }
    }
}

@Preview
@Composable
fun HomeNavGraphPreview() {
    HomeNavGraph(rememberNavController())
}