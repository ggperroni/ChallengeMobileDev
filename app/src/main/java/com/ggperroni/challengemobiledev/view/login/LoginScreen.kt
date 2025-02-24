package com.ggperroni.challengemobiledev.view.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ggperroni.challengemobiledev.R
import com.ggperroni.challengemobiledev.domain.login.LoginState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()

    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onNavigateToHome(username)
        }
        if (state is LoginState.Error || state is LoginState.Success) {
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFFF3B67)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Icon",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF3B67)),
            shape = RoundedCornerShape(topStart = 150.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Welcome",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF3B67)
                )
                Spacer(modifier = Modifier.height(100.dp))
                OutlinedTextField(
                    value = username,
                    shape = RoundedCornerShape(25.dp),
                    onValueChange = { viewModel.onUsernameChange(it) },
                    label = { Text("User") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = password,
                    shape = RoundedCornerShape(25.dp),
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        viewModel.login()
                    },
                    enabled = username.isNotEmpty() && password.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B67)),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    if (state is LoginState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "LOGIN",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                when (state) {
                    is LoginState.Error -> {
                        Toast.makeText(
                            LocalContext.current,
                            "Error: ${(state as LoginState.Error).message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginState.Success -> {
                        Toast.makeText(
                            LocalContext.current,
                            "Login successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {}
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        onNavigateToHome = {}
    )
}
