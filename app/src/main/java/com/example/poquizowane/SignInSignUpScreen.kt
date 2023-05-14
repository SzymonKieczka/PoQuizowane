package com.example.poquizowane

import AuthViewModel
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSignUpScreen(authViewModel: AuthViewModel) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.login_animation)
    )
    var confirmPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var isSignUp by rememberSaveable { mutableStateOf(false) }
    val cardWeight by animateFloatAsState(if (isSignUp) 1.5f else 1f)
    val context = LocalContext.current

    PoQuizowaneTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0, 151, 91)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box (
                modifier = Modifier
                    .weight(1f)
                    .size(180.dp)
            )
            {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Card(
                Modifier
                    .weight(cardWeight)
                    .padding(12.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "E-mail") },
                            placeholder = { Text(text = "Enter e-mail") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        OutlinedTextField(
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description = if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = {passwordVisible = !passwordVisible}){
                                    Icon(imageVector  = image, description)
                                }
                            },
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            placeholder = { Text(text = "Enter password") },
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        AnimatedVisibility(visible = isSignUp) {
                            OutlinedTextField(
                                trailingIcon = {
                                    val image = if (confirmPasswordVisible)
                                        Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff

                                    val description = if (confirmPasswordVisible) "Hide password" else "Show password"

                                    IconButton(onClick = {confirmPasswordVisible = !confirmPasswordVisible}){
                                        Icon(imageVector  = image, description)
                                    }
                                },
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                label = { Text("Confirm Password") },
                                placeholder = { Text(text = "Confirm password") },
                                singleLine = true,
                                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
                                else PasswordVisualTransformation(),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(bottom = 16.dp)
                            )

                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                Color(0, 151, 91),
                            ),
                            onClick = {
                                if(isSignUp)
                                    if (password.text == confirmPassword.text) {
                                        authViewModel.signUp(context, email.text, password.text)
                                    }
                                    else
                                        Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
                                else {
                                    authViewModel.signIn(context, email.text, password.text)
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(60.dp)
                        )
                        {
                            if (isSignUp)
                                Text("Sign up")
                            else
                                Text("Sign in")
                        }

                        Text(text = "or", fontSize = 14.sp, modifier = Modifier.padding(10.dp))

                        OutlinedButton(
                            onClick = {
                                isSignUp = !isSignUp
                            },
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(60.dp)
                        )
                        {
                            if (isSignUp)
                                Text("Already have an account? Sign in", fontSize = 14.sp)
                            else
                                Text("Don't have an account? Sign up", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

    }
}


