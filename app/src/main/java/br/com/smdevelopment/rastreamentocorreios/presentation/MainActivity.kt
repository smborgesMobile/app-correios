package br.com.smdevelopment.rastreamentocorreios.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import br.com.smdevelopment.rastreamentocorreios.presentation.components.CustomTopAppBar
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DrawerBody
import br.com.smdevelopment.rastreamentocorreios.presentation.components.DrawerHeader
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.HomeBottomNavigation
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.NavigationGraph
import br.com.smdevelopment.rastreamentocorreios.ui.theme.RastreamentoCorreiosTheme
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreenView()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview
fun MainScreenView() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        },
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar(onNavigationClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            })
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