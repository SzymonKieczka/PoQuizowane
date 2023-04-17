package com.example.poquizowane

import AuthViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val authViewModel = AuthViewModel()
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        firebaseAuth = FirebaseAuth.getInstance()


        super.onCreate(savedInstanceState)
        setContent {
            PoQuizowaneTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0, 151, 91)
                ) {
                    SignInSignUpScreen(authViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
