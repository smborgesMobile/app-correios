package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.firebase.FIREBASE_KEYS
import br.com.smdevelopment.rastreamentocorreios.presentation.components.PrimaryButton
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.MAIN_ROUTE
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary500
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val loginState by loginViewModel.googleState.collectAsState()

    when (loginState) {
        is LoginViewModel.GoogleState.Success -> {
            NavigateHome(navController)
        }

        is LoginViewModel.GoogleState.Error -> {
            Toast.makeText(context, stringResource(R.string.fail_to_log_in), Toast.LENGTH_SHORT)
                .show()
        }

        else -> {

        }
    }

    // Check Firebase Authentication state
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    // If the user is already logged in, navigate to the main screen
    if (currentUser != null) {
        NavigateHome(navController)
    } else {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                loginViewModel.googleSignIn(credentials)
            } catch (ex: Exception) {
                print(ex)
            }
        }

        val gson = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(FIREBASE_KEYS)
            .build()

        // If the user is not logged in, show the login button
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(
                        shape = RoundedCornerShape(bottomStart = 120.dp)
                    )
                    .background(primary500),
                contentAlignment = Alignment.Center // Align the content in the center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.delivered_start_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        color = Color.White,
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp),
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxSize()
            ) {
                PrimaryButton(
                    title = stringResource(R.string.google_login),
                    loading = (loginState as? LoginViewModel.GoogleState.Loading)?.isLoading
                        ?: false,
                    icon = R.drawable.logo_google
                ) {
                    val signInClient = GoogleSignIn.getClient(context, gson)
                    launcher.launch(signInClient.signInIntent)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun NavigateHome(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(primary500),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.delivered_start_icon),
            contentDescription = null,
            alignment = Alignment.Center
        )
        navController.navigate(MAIN_ROUTE) {
            popUpTo(navController.graph.startDestinationRoute.orEmpty()) {
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}