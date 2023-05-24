package com.example.poquizowane

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.poquizowane.ui.theme.PoQuizowaneTheme

class QuizSummaryActivity : ComponentActivity() {
    var correctAnswers: Int = 0;
    var totalQuestions: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        correctAnswers = intent.getIntExtra("correctAnswers", 0)
        totalQuestions = intent.getIntExtra("totalQuestions", 0)

        val percent = (correctAnswers.toDouble() / totalQuestions.toDouble() * 100).toInt().toString() + "%"

        setContent {
            PoQuizowaneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackgroundImage()
                    QuizSummary(percent)
                }
            }
        }
    }

    @Composable
    fun BackgroundImage() {
        Image(
            painter = painterResource(id = R.drawable.quiz_selection_screen),
            contentDescription = "pic",
            contentScale = ContentScale.FillBounds
        )
    }

    @Composable
    fun MyButton(text: String, onclick: () -> Unit) {
        androidx.compose.material.Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = { onclick() },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(60.dp)
        ) {
            Text(text, fontSize = 16.sp, color = Color(0, 151, 91))
        }
    }

    @Composable
    fun QuizSummary(percent: String) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.success)
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
                    iterations = 1,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.padding(all = 10.dp))
            Column(
                Modifier
                    .weight(1.5f)
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = "Your score:",
                    fontSize = 24.sp,
                    color = Color.White)
                Text(text = percent,
                    fontSize = 64.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.padding(15.dp))
                Buttons()
            }
        }
    }

    @Composable
    fun Buttons() {
        MyButton(text = "Try again") {
            val intent = Intent(this, QuizSelectActivity::class.java)
            startActivity(intent)
        }
        Spacer(modifier = Modifier.padding(15.dp))
        MyButton(text = "Another quiz") {
            val intent = Intent(this, QuizSelectActivity::class.java)
            startActivity(intent)
        }
        Spacer(modifier = Modifier.padding(15.dp))
        MyButton(text = "Main menu") {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        Spacer(modifier = Modifier.padding(15.dp))
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PoQuizowaneTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                BackgroundImage()
                QuizSummary("50%")
            }
        }
    }
}





