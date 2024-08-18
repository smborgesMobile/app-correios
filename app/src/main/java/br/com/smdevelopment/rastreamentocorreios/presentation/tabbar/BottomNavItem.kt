package br.com.smdevelopment.rastreamentocorreios.presentation.tabbar

import br.com.smdevelopment.rastreamentocorreios.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    data object Delivered : BottomNavItem(DELIVERED, R.drawable.delivered_bs_icon, DELIVERED.lowercase())
    data object Home : BottomNavItem(HOME, R.drawable.home_bs_icon, HOME.lowercase())
    data object Pending : BottomNavItem(IN_PROGRESS, R.drawable.in_progress_bs_icon, IN_PROGRESS.lowercase())
    data object Price : BottomNavItem(PRICE, R.drawable.price_bs_icon, PRICE.lowercase())

    private companion object {
        const val HOME = "Home"
        const val DELIVERED = "Entregue"
        const val IN_PROGRESS = "Pendentes"
        const val PRICE = "Price"
    }
}
