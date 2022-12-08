package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu

import br.com.smdevelopment.rastreamentocorreios.R

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object About : NavDrawerItem(ABOUT.lowercase(), R.drawable.icon_about, ABOUT)
    object Settings : NavDrawerItem(SETTINGS.lowercase(), R.drawable.icon_running, SETTINGS)

    private companion object {
        const val ABOUT = "About"
        const val SETTINGS = "settings"
    }
}