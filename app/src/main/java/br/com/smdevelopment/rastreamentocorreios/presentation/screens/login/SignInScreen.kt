package br.com.smdevelopment.rastreamentocorreios.presentation.screens.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.firebase.FIREBASE_KEYS
import br.com.smdevelopment.rastreamentocorreios.presentation.components.PrimaryButton
import br.com.smdevelopment.rastreamentocorreios.presentation.navigation.tabbar.MAIN_ROUTE
import br.com.smdevelopment.rastreamentocorreios.ui.theme.primary500
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
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
    }

    // If the user is already logged in, navigate to the main screen
    if (loginViewModel.currentUser != null) {
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
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        shape = RoundedCornerShape(bottomStart = 120.dp)
                    )
                    .background(primary500),
                contentAlignment = Alignment.Center // Align the content in the center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delivered_start_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                )
            }

            OutlinedTextField(
                value = loginState.userEmail,
                singleLine = true,
                onValueChange = { newCode ->
                    loginViewModel.onEmailChange(newCode)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 16.dp
                    ),
                label = { Text(text = stringResource(id = R.string.email_label)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = colorResource(id = R.color.text_field_border_color),
                    cursorColor = colorResource(id = R.color.text_field_border_color),
                    focusedLabelColor = colorResource(id = R.color.text_field_border_color),
                    textColor = colorResource(id = R.color.text_field_editable_color)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = loginState.userPassword,
                singleLine = true,
                onValueChange = { newCode ->
                    loginViewModel.onPasswordChanged(newCode)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                label = { Text(text = stringResource(id = R.string.password_label)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = colorResource(id = R.color.text_field_border_color),
                    cursorColor = colorResource(id = R.color.text_field_border_color),
                    focusedLabelColor = colorResource(id = R.color.text_field_border_color),
                    textColor = colorResource(id = R.color.text_field_editable_color)
                ),
                visualTransformation = if (true) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { loginViewModel.changePasswordEyes(!loginState.showPasswordEyes) },
                        modifier = Modifier.clickable { loginViewModel.changePasswordEyes(!loginState.showPasswordEyes) },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.password_eyes),
                            contentDescription = null,
                            alignment = Alignment.Center
                        )
                    }
                }
            )
            PrimaryButton(
                title = stringResource(R.string.create_account),
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                loading = loginState.showCreateUserLoading,
                onCodeClick = loginViewModel::createAccount,
                enabled = loginState.enableCreateAccountButton
            )
            PrimaryButton(
                title = stringResource(R.string.login),
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                enabled = loginState.enableLoginButton,
                onCodeClick = loginViewModel::loginUser,
                loading = loginState.showLoginLoading,
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            PrimaryButton(
                title = stringResource(R.string.google_login),
                loading = loginState.showGoogleButtonLoading,
                icon = R.drawable.logo_google,
                primaryColor = primary500
            ) {
                val signInClient = GoogleSignIn.getClient(context, gson)
                launcher.launch(signInClient.signInIntent)
            }
            Spacer(modifier = Modifier.height(24.dp))
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

@Preview
@Composable
private fun LoginPreview() {
    LoginScreen(navController = rememberNavController())
}