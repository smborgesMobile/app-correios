package br.com.smdevelopment.rastreamentocorreios.presentation.screens.newLogin

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.components.NewButton
import br.com.smdevelopment.rastreamentocorreios.presentation.components.TextFieldComponent
import br.com.smdevelopment.rastreamentocorreios.presentation.components.TextFieldType
import br.com.smdevelopment.rastreamentocorreios.presentation.components.Toolbar
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.MAIN_ROUTE
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.login.LoginEvent
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.login.LoginUiState
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewLoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = koinViewModel()
    val loginState: LoginUiState by loginViewModel.loginUiState.collectAsState()

    when {
        loginState.showLoginSuccess || loginState.showGoogleLoginSuccess || loginState.showCreateUserSuccess -> {
            NavigateHome(navController)
        }

        loginState.showCreateUserError || loginState.showGoogleLoginError -> {
            Toast.makeText(
                context,
                stringResource(R.string.fail_to_sign_in),
                Toast.LENGTH_SHORT
            ).show()
        }

        loginState.showLoginError -> {
            Toast.makeText(
                context,
                stringResource(R.string.fail_to_log_in),
                Toast.LENGTH_SHORT
            ).show()
        }

        loginState.showChangePasswordError -> {
            Toast.makeText(
                context,
                stringResource(R.string.fail_to_change_password),
                Toast.LENGTH_SHORT
            ).show()
        }

        loginState.showChangePasswordSuccess -> {
            Toast.makeText(
                context,
                stringResource(R.string.success_to_send_reset_password),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (loginViewModel.currentUser != null) {
        NavigateHome(navController)
    } else {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                loginViewModel.onEvent(LoginEvent.GoogleSignIn(credentials))
            } catch (ex: Exception) {
                print(ex)
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Toolbar {
                (context as Activity).finish()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    fontStyle = MaterialTheme.typography.h1.fontStyle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldComponent(
                hint = stringResource(R.string.email_label),
                modifier = Modifier.padding(horizontal = 16.dp),
                type = TextFieldType.EMAIL,
                onValueChange = {
                    loginViewModel.onEvent(LoginEvent.EmailChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextFieldComponent(
                hint = "Password",
                modifier = Modifier.padding(horizontal = 16.dp),
                type = TextFieldType.PASSWORD,
                onValueChange = {
                    loginViewModel.onEvent(LoginEvent.PasswordChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Forgot Password",
                fontStyle = MaterialTheme.typography.body1.fontStyle,
                color = Color(0xFF637587),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { loginViewModel.onEvent(LoginEvent.SendChangePasswordEmail) },
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NewButton(
                    text = "Sign In",
                    onClick = { loginViewModel.onEvent(LoginEvent.LoginUser) },
                    modifier = Modifier.weight(1f),
                    isPrimary = true
                )
                NewButton(
                    text = "Sign Up",
                    onClick = { loginViewModel.onEvent(LoginEvent.CreateAccount) },
                    modifier = Modifier.weight(1f),
                    isPrimary = false
                )
            }
        }
    }
}

@Composable
private fun NavigateHome(navController: NavController) {
    navController.navigate(MAIN_ROUTE) {
        popUpTo(navController.graph.startDestinationRoute.orEmpty()) {
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
@Preview
fun NewLoginScreenPreview() {
    NewLoginScreen(navController = rememberNavController())
}
