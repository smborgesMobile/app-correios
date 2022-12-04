package br.com.smdevelopment.rastreamentocorreios.presentation

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Event
import br.com.smdevelopment.rastreamentocorreios.presentation.components.CustomTopAppBar
import br.com.smdevelopment.rastreamentocorreios.ui.theme.RastreamentoCorreiosTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RastreamentoCorreiosTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    DetailScreen()
                }
            }
        }
    }

    companion object {
        private const val DELIVERY_DATA = "DELIVERY_DATA"

        fun getLaunchIntent(context: Context, deliveryList: List<Event>) = Intent(context, DetailActivity::class.java).apply {
            putParcelableArrayListExtra(DELIVERY_DATA, ArrayList(deliveryList))
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar() }) {

    }
}