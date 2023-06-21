package com.example.poquizowane

import AuthViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private val authViewModel = AuthViewModel()
    private lateinit var firebaseAuth: FirebaseAuth
    private val quizList = mutableStateListOf<Quiz>()
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        firebaseAuth = FirebaseAuth.getInstance()

        db.collection("quizzes").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val quiz = document.toObject(Quiz::class.java)
                quizList.add(quiz)
            }
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }


        super.onCreate(savedInstanceState)
        setContent {
            PoQuizowaneTheme {
                Surface (
                    modifier = Modifier.fillMaxSize().testTag("signinsignupscreen"),
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
            intent.putExtra("quizList", quizList.toTypedArray())
            startActivity(intent)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
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
