package com.example.poquizowane

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.relay.compose.BoxScopeInstanceImpl.align


class LeaderboardActivity : ComponentActivity() {

    private val db = Firebase.firestore
    private val userList = mutableStateListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db.collection("users").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val user = document.toObject(User::class.java)
                userList.add(user)
            }
            userList.sortByDescending { it.quizScores.size }
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }

        setContent {
            PoQuizowaneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackgroundImage()
                    LeaderBoard(userList)
                }
            }
        }
    }

    @Composable
    fun BackgroundImage() {
        Image(
            painter = painterResource(id = R.drawable.leaderboards_screen),
            contentDescription = "pic",
            contentScale = ContentScale.FillBounds
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LeaderBoard(users: List<User>) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.leader)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //.background(Color(0, 151, 91)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(180.dp)
            )
            {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.padding(all = 10.dp))
            Column (
                Modifier
                    .weight(1.5f)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                users.forEachIndexed { index, user ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                            .height(100.dp) // Set the height as per your need
                            .border(1.dp, Color.White, RoundedCornerShape(48.dp)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("${index + 1}. ${user.email}", fontSize = 24.sp, color = Color.White)
                        Spacer(Modifier.height(4.dp))
                        Text("Completed quizzes: ${user.quizScores.size}", fontSize = 16.sp, color = Color.White)
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PoQuizowaneTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                BackgroundImage()
            }
        }
    }


}