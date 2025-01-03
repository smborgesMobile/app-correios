package br.com.smdevelopment.rastreamentocorreios.presentation

import androidx.lifecycle.ViewModel
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.GetPriceFeatureToggleUseCase
import br.com.smdevelopment.rastreamentocorreios.presentation.tabbar.BottomNavItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val getPriceFeatureToggleUseCase: GetPriceFeatureToggleUseCase
) : ViewModel() {

    private val _showDeleteDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> get() = _showDeleteDialog

    private val _showLogoutDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> get() = _showLogoutDialog

    private val _sideMenuUiState: MutableStateFlow<SideMenuUiState> =
        MutableStateFlow(SideMenuUiState.Nothing)
    val sideMenuUiState: StateFlow<SideMenuUiState> get() = _sideMenuUiState


    //#region --- delete

    fun onDeleteDismissClick() {
        _showDeleteDialog.value = false
    }

    fun getBottomNavigationItems(): List<BottomNavItem> {
        val itemList = mutableListOf(
            BottomNavItem.Delivered,
            BottomNavItem.Home,
            BottomNavItem.Pending
        )

        if (getPriceFeatureToggleUseCase.isPriceToggleEnabled()) {
            itemList.add(BottomNavItem.Price)
        }

        return itemList
    }


    fun onDeleteDialogClick() {
        _showDeleteDialog.value = false
        deleteAccount()
    }

    fun onDeleteButtonClick() {
        _showDeleteDialog.value = true
    }

    private fun deleteAccount() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _sideMenuUiState.value = SideMenuUiState.DeleteAccount
                } else {
                    _sideMenuUiState.value = SideMenuUiState.DeleteAccountError
                }
            }
    }

    //#endregion --- delete

    //#region --- logout

    fun onLogoutDismissed() {
        _showLogoutDialog.value = false
    }

    fun onLogoutConfirm() {
        _showLogoutDialog.value = false
        _sideMenuUiState.value = SideMenuUiState.Login
        FirebaseAuth.getInstance().signOut()
        FirebaseAuth.getInstance().currentUser?.delete()
    }

    fun onLogoutButtonClick() {
        _showLogoutDialog.value = true
    }

    fun resetSideMenuUiState() {
        _sideMenuUiState.value = SideMenuUiState.Nothing
    }

    //#endregion --- logout

    sealed interface SideMenuUiState {
        data object Login : SideMenuUiState
        data object DeleteAccount : SideMenuUiState
        data object DeleteAccountError : SideMenuUiState
        data object Nothing : SideMenuUiState
    }
}