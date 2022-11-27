package br.com.smdevelopment.rastreamentocorreios.presentation.home.tabbar

import br.com.smdevelopment.rastreamentocorreios.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Car : BottomNavItem(CAR, R.drawable.icon_car, CAR.lowercase())
    object Home : BottomNavItem(HOME, R.drawable.icon_home, HOME.lowercase())
    object Star : BottomNavItem(STAR, R.drawable.icon_star, STAR.lowercase())

    private companion object {
        const val HOME = "Home"
        const val CAR = "Car"
        const val STAR = "Star"
    }
}
