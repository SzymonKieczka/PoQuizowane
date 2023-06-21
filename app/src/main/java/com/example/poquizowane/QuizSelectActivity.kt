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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.relay.compose.BoxScopeInstanceImpl.align


class QuizSelectActivity : ComponentActivity() {
    private val db = Firebase.firestore
    private val quizList = mutableStateListOf<Quiz>()
    private val userRepository = UserRepository(Firebase.firestore)
    private var user: User? = null
    private lateinit var auth: FirebaseAuth
    val QUIZTAG = "quiz"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val userId = auth.currentUser?.uid
        userId?.let {
            userRepository.getUser(it,
                onSuccess = { fetchedUser ->
                    user = fetchedUser
                },
                onFailure = { exception ->
                    println("Error getting user: $exception")
                }
            )
        }

        db.collection("quizzes").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val quiz = document.toObject(Quiz::class.java)
                quizList.add(quiz)
            }
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }


        setContent {
            PoQuizowaneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackgroundImage()
                    QuizSelect(quizList, this)
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
    fun MyButton(quiz: Quiz, user: User?, onclick: () -> Unit) {
        androidx.compose.material.Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = { onclick() },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(100.dp)
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp).testTag(QUIZTAG),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Text(quiz.name, fontSize = 24.sp, color = Color(0, 151, 91))
                    Text(quiz.category, fontSize = 12.sp, color = Color(0, 151, 91))

                    // Displaying the best score if the user has taken the quiz
                    user?.quizScores?.get(quiz.id)?.let { bestScore ->
                        Text("Best score: $bestScore", fontSize = 12.sp, color = Color(0, 151, 91))
                    }
                }

                Spacer(Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.End
                            ){
                        user?.quizScores?.containsKey(quiz.id)?.let { hasTakenQuiz ->
                            if (hasTakenQuiz) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                                    contentDescription = "Completed icon",
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }

                    Row {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .border(1.dp, Color(0, 151, 91), CircleShape)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = when (quiz.difficulty) {
                                    "hard" -> "H"
                                    "medium" -> "M"
                                    "easy" -> "E"
                                    else -> "N/A"
                                },
                                fontSize = 12.sp,
                                color = Color(0, 151, 91)
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .border(1.dp, Color(0, 151, 91), CircleShape)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${quiz.questions.size}",
                                fontSize = 12.sp,
                                color = Color(0, 151, 91)
                            )
                        }
                    }
                }
            }
        }
    }




    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun QuizSelect(quizList: List<Quiz>, context: Context) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.question)
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
            LazyColumn (
                Modifier
                    .weight(1.5f)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                items(quizList) { quiz ->
                    MyButton(quiz = quiz, user = user) {
                        val intent = Intent(context, QuizPlayActivity::class.java)
                        intent.putExtra("quiz", quiz)
                        startActivity(intent)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    /*
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PoQuizowaneTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                BackgroundImage()
                QuizSelect(quizList, this)
            }
        }
    }
     */
}





