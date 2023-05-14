package com.example.poquizowane

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoQuizowaneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BackgroundImage()
                    Buttons()
                }
            }
        }
    }

    @Composable
    fun BackgroundImage() {
        Image(painter = painterResource(id = R.drawable.homescreenpic),
            contentDescription = "pic",
            contentScale = ContentScale.FillBounds
        )
    }

    @Composable
    fun LogoutButton() {
        Column(
            //modifier = Modifier.fillMaxSize(),
            modifier = Modifier.padding(all = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    Firebase.auth.signOut()
                    finish()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Logout")
            }
        }
    }

    @Composable
    fun MyButton(text: String, onclick: () -> Unit) {
        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = { onclick() },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(60.dp)
        ) {
            Text(text, fontSize = 14.sp, color = Color(0, 151, 91))
        }
    }

    @Composable
    fun Buttons() {
        Column(
            modifier = Modifier.padding(all = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            MyButton("Play") { play() }
            Spacer(modifier = Modifier.padding(10.dp))
            MyButton("Create") { create() }
            Spacer(modifier = Modifier.padding(10.dp))
            MyButton("Leaderboard") { leaderboard() }
            Spacer(modifier = Modifier.padding(10.dp))
            MyButton("Log out") { logout() }
        }
    }


    @Preview
    @Composable
    fun prev() {
        PoQuizowaneTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
                //color = MaterialTheme.colors.background
            ) {
                BackgroundImage()
                Buttons()
            }
        }
    }

    private fun play(){
        val intent = Intent(this, QuizSelectActivity::class.java)
        startActivity(intent)
    }

    private fun create(){
        val intent = Intent(this, CreateQuizActivity::class.java)
        startActivity(intent)
    }

    private fun leaderboard(){
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        Firebase.auth.signOut()
        //finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}