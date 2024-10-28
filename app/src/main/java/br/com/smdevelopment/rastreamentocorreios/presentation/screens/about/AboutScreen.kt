package br.com.smdevelopment.rastreamentocorreios.presentation.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.extensions.sendMail

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.warning_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )

            Text(
                text = stringResource(id = R.string.about_screen_description),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.about_screen_info),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(id = R.string.about_sac_title),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.about_screen_contacts),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.link_track),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp),
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
            ) {
                Button(
                    onClick = {
                        context.sendMail(
                            to = context.getString(R.string.app_email),
                            subject = context.getString(R.string.email_subject)
                        )
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .align(Alignment.Bottom)
                ) {
                    Text(text = stringResource(id = R.string.about_screen_button_title))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowAboutPreview() {
    AboutScreen()
}