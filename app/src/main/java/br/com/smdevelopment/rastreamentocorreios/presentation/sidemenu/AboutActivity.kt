package br.com.smdevelopment.rastreamentocorreios.presentation.sidemenu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.smdevelopment.rastreamentocorreios.presentation.components.CustomTopAppBar
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.about.AboutScreen
import br.com.smdevelopment.rastreamentocorreios.ui.theme.RastreamentoCorreiosTheme
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class AboutActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CustomTopAppBar(
                                hasBackButton = true,
                                endMargin = 32.dp,
                                closeActivityListener = { finish() }
                            )
                        }) {
                        AboutScreen()
                    }
                }
            }
        }
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, AboutActivity::class.java)
    }
}