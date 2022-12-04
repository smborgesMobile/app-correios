package br.com.smdevelopment.rastreamentocorreios.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Event
import br.com.smdevelopment.rastreamentocorreios.ui.theme.RastreamentoCorreiosTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RastreamentoCorreiosTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RastreamentoCorreiosTheme {
        Greeting("Android")
    }
}