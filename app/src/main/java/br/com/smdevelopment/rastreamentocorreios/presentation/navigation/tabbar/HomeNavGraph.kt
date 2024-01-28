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

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition = { fadeIn(animationSpec = tween(5)) },
        exitTransition = { fadeOut(animationSpec = tween(5)) }
    ) {

        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("main_screen") {
            MainScreenView()
        }
    }
}

@Preview
@Composable
fun HomeNavGraphPreview() {
    HomeNavGraph(rememberNavController())
}