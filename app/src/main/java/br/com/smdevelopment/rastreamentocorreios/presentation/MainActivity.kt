package br.com.smdevelopment.rastreamentocorreios.presentation

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun MainScreenView(homeNavController: NavHostController) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    CustomAlertDialog(showDialog = showDeleteDialog, onDismiss = {
        showDeleteDialog = false
    }, onExit = {
        showDeleteDialog = false
    },
        titleRes = R.string.delete_account_title,
        descriptionRes = R.string.delete_account_message
    )

    CustomAlertDialog(showDialog = showLogoutDialog, onDismiss = {
        showLogoutDialog = false
    }, onExit = {
        FirebaseAuth.getInstance().signOut()
        homeNavController.navigate(LOGIN_ROUTE) {
            popUpTo(MAIN_ROUTE) {
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
        showLogoutDialog = false
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
                                showDeleteDialog = true
                            }

                            SIGN_OUT -> {
                                showLogoutDialog = true
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
                }, endMargin = 16.dp
            )
        },
        bottomBar = {
            HomeBottomNavigation(navController = navController)
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

//#endregion --- composes