package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu

import br.com.smdevelopment.rastreamentocorreios.R

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object About : NavDrawerItem(ABOUT.lowercase(), R.drawable.icon_about, ABOUT)

    private companion object {
        const val ABOUT = "About"
    }
}