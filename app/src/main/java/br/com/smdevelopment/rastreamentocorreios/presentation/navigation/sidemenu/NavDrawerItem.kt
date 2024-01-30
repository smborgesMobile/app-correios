package br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu

import br.com.smdevelopment.rastreamentocorreios.R

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object About : NavDrawerItem(ABOUT.lowercase(), R.drawable.icon_about, ABOUT)
    object DeleteAccount :
        NavDrawerItem(DELETE.lowercase(), R.drawable.ic_delete_account, DELETE)

    object SignOut :
        NavDrawerItem(SIGN_OUT.lowercase(), R.drawable.ic_sign_out, SIGN_OUT)

    companion object {
        const val ABOUT = "About"
        const val DELETE = "Deletar Conta"
        const val SIGN_OUT = "Sign Out"
    }
}