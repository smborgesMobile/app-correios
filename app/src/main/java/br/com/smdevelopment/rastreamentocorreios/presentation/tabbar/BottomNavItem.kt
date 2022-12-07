package br.com.smdevelopment.rastreamentocorreios.presentation.tabbar

import br.com.smdevelopment.rastreamentocorreios.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Delivered : BottomNavItem(DELIVERED, R.drawable.icon_car, DELIVERED.lowercase())
    object Home : BottomNavItem(HOME, R.drawable.icon_home, HOME.lowercase())
    object Pending : BottomNavItem(IN_PROGRESS, R.drawable.icon_running, IN_PROGRESS.lowercase())
    object About : BottomNavItem(ABOUT, R.drawable.icon_about, ABOUT.lowercase())

    private companion object {
        const val HOME = "Home"
        const val DELIVERED = "Entregue"
        const val IN_PROGRESS = "Pendentes"
        const val ABOUT = "Sobre"
    }
}
