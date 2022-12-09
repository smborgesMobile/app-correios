package br.com.smdevelopment.rastreamentocorreios.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventData
import br.com.smdevelopment.rastreamentocorreios.presentation.components.CustomTopAppBar
import br.com.smdevelopment.rastreamentocorreios.ui.theme.RastreamentoCorreiosTheme
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary700
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val eventList: List<EventData> = intent.getParcelableArrayListExtra<EventData>(DELIVERY_DATA)?.toList() ?: emptyList()

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
                    DetailScreen(eventList) {
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        private const val DELIVERY_DATA = "DELIVERY_DATA"

        fun getLaunchIntent(context: Context, deliveryList: List<EventData>) = Intent(context, DetailActivity::class.java).apply {
            putParcelableArrayListExtra(DELIVERY_DATA, ArrayList(deliveryList))
        }
    }
}

//#region --- composes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(eventList: List<EventData>, closeActivityListener: (() -> Unit)) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                hasBackButton = true,
                endMargin = 32.dp,
                closeActivityListener = closeActivityListener
            )
        }) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(eventList) { event ->
                Row {
                    Image(
                        painter = painterResource(event.iconUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(8.dp, top = 16.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    ) {
                        Text(
                            text = event.formattedDestination,
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 14.sp
                        )
                        Text(
                            text = event.description,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = event.date,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 12.sp
                        )
                    }
                }
                Divider()
            }
        }
    }
}

//#endregion --- composes