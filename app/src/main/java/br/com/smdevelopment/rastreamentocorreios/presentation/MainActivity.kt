package br.com.smdevelopment.rastreamentocorreios.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.smdevelopment.rastreamentocorreios.BuildConfig
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.components.CustomTopAppBar
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DrawerBody
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DrawerFooter
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DrawerHeader
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu.NavDrawerItem.Companion.ABOUT
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu.NavDrawerItem.Companion.DELETE
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.sidemenu.NavDrawerItem.Companion.SIGN_OUT
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.HomeBottomNavigation
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.HomeNavGraph
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.LOGIN_ROUTE
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.MAIN_ROUTE
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.NavigationGraph
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.CustomAlertDialog
import br.com.smdevelopment.rastreamentocorreios.presentation.sidemenu.AboutActivity
import br.com.smdevelopment.rastreamentocorreios.ui.theme.RastreamentoCorreiosTheme
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sborges.core.review.manager.ReviewManagerLauncher
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val inAppReviewLauncher: ReviewManagerLauncher by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inAppReviewLauncher.launchInAppReview(this)

        setContent {
            RastreamentoCorreiosTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = primary700,
                        darkIcons = false
                    )
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeNavGraph(navController = rememberNavController())
                }
            }
        }
    }
}

//#region --- composes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView(
    homeNavController: NavHostController,
    mainViewModel: MainViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val showDeleteDialog by mainViewModel.showDeleteDialog.collectAsState()
    val showLogoutDialog by mainViewModel.showLogoutDialog.collectAsState()
    val uiState by mainViewModel.sideMenuUiState.collectAsState()

    when (uiState) {
        is MainViewModel.SideMenuUiState.DeleteAccount -> {
            NavigateToLogin(homeNavController)
        }

        is MainViewModel.SideMenuUiState.DeleteAccountError -> {
            mainViewModel.resetSideMenuUiState()
            Toast.makeText(
                context,
                stringResource(R.string.fail_to_delete_account),
                Toast.LENGTH_SHORT
            ).show()
        }

        is MainViewModel.SideMenuUiState.Login -> {
            NavigateToLogin(homeNavController)
        }

        else -> {}
    }

    CustomAlertDialog(showDialog = showDeleteDialog, onDismiss = {
        mainViewModel.onDeleteDismissClick()
    }, onExit = {
        mainViewModel.onDeleteDialogClick()
    },
        titleRes = R.string.delete_account_title,
        descriptionRes = R.string.delete_account_message
    )

    CustomAlertDialog(showDialog = showLogoutDialog, onDismiss = {
        mainViewModel.onLogoutDismissed()
    }, onExit = {
        mainViewModel.onLogoutConfirm()
    },
        titleRes = R.string.sign_out_title,
        descriptionRes = R.string.sign_out_message
    )


    Scaffold(
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Column {
                DrawerHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primary700)
                        .weight(0.1f)
                )
                DrawerBody(
                    modifier = Modifier.weight(1f),
                    onItemClick = { item ->
                        when (item.title) {
                            ABOUT -> {
                                context.startActivity(AboutActivity.getLaunchIntent(context))
                            }

                            DELETE -> {
                                mainViewModel.onDeleteButtonClick()
                            }

                            SIGN_OUT -> {
                                mainViewModel.onLogoutButtonClick()
                            }
                        }

                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    })
                DrawerFooter(
                    text = "Versão ${BuildConfig.VERSION_NAME}",
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
        },
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar(
                onNavigationClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }, endMargin = 42.dp
            )
        },
        bottomBar = {
            HomeBottomNavigation(
                navController = navController,
                items = mainViewModel.getBottomNavigationItems()
            )
        }) {
        Column(
            modifier = Modifier.background(primary700)
        ) {
            Card(
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(modifier = Modifier.padding(bottom = 60.dp)) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
private fun NavigateToLogin(homeNavController: NavHostController) {
    homeNavController.navigate(LOGIN_ROUTE) {
        popUpTo(MAIN_ROUTE) {
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

//#endregion --- composes