package com.example.twittertest.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lib_data.util.Constants.login
import com.example.lib_data.util.Resource
import com.example.twittertest.InputType
import com.example.twittertest.R
import com.example.twittertest.TextInput

/**
 *
 */
@Composable
fun LoginPage(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()

) {
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current
    val token = viewModel.user.collectAsState().value
    LaunchedEffect(key1 = token) {
        when (token) {
            is Resource.Error -> {}
            Resource.Loading -> {}
            is Resource.Success -> login(navController)
            null -> {}
        }
    }
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(13.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.twitter_logo),
            null,
            Modifier.size(80.dp),
            tint = Color.White
        )
        TextInput(InputType.userName, keyboardActions = KeyboardActions(onNext = {
            passwordFocusRequester.requestFocus()

        }), focusRequester = null) { username ->
            viewModel.setUsername(username)
            username
        }
        TextInput(InputType.password, keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }), focusRequester = passwordFocusRequester) { password ->
            viewModel.setPassword(password)
            password
        }
        Button(
            onClick = {
                viewModel.loginUser()
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Sign In", modifier = Modifier.padding(vertical = .8.dp))
            Divider(modifier = Modifier.padding(top = 48.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?", color = Color.White)
                TextButton(onClick = {
                    println("Sign Up Button clicked")
                    navController.navigate("signUp_screen")
                }) {
                    Text("Sign Up")
                }
            }
        }
    }
}





