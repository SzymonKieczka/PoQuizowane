package com.example.poquizowane

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.poquizowane.ui.theme.PoQuizowaneTheme
import java.util.*

class QuizPlayActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    var currentQuestion = 0
    var correctAnswers = 0
    lateinit var quiz: Quiz
    private var tts: TextToSpeech? = null

    override fun onInit(p0: Int) {
        tts!!.setLanguage(Locale.US)
    }

    override fun onDestroy() {
        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    fun say(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quiz = intent.getSerializableExtra("quiz") as Quiz

        setContent {
            PoQuizowaneTheme {
                Surface(
                    color = Color(0, 151, 91),
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackgroundImage()
                    QuestionView(quiz.questions[currentQuestion]) { answer ->
                        checkAnswer(answer)
                    }
                }
            }
        }
        tts = TextToSpeech(this, this)
    }

    @Composable
    fun BackgroundImage() {
        Image(
            painter = painterResource(id = R.drawable.quiz_selection_screen),
            contentDescription = "pic",
            contentScale = ContentScale.FillBounds
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun QuestionView(question: Question, onAnswerClick: (String) -> Unit) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Question ${currentQuestion + 1}/${quiz.questions.size}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0, 151, 91),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
                if (question.imageUrl != null && question.imageUrl != "") {
                    Row(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {
                        AsyncImage(
                            model = question.imageUrl,
                            contentDescription = question.description,
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = question.description,
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier
                .weight(0.2f)
                .padding(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Surface(
                    color = Color(0, 151, 91)
                ) {
                    Text(
                        text = question.description,
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Italic
                        //style = MaterialTheme.typography.labelLarge
                    )
                }

                QuestionReader(question)

                val mod = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                Spacer(modifier = Modifier.padding(10.dp))
                AnswerButton(question.answerA, onAnswerClick, "A", mod)
                Spacer(modifier = Modifier.padding(10.dp))
                AnswerButton(question.answerB, onAnswerClick, "B", mod)
                Spacer(modifier = Modifier.padding(10.dp))
                AnswerButton(question.answerC, onAnswerClick, "C", mod)
                Spacer(modifier = Modifier.padding(10.dp))
                AnswerButton(question.answerD, onAnswerClick, "D", mod)


            }
        }
    }

    @Composable
    fun QuestionReader(question: Question) {
        IconButton(
            onClick = {
                val str = "${question.description}... A... ${question.answerA}, B... ${question.answerB}, C... ${question.answerC}, D... ${question.answerD}."
                say(str)
            }) {
            Icon(
                tint = Color.White,
                painter = painterResource(id = R.drawable.baseline_volume_up_24),
                contentDescription = "Speak text"
            )
        }
    }

    @Composable
    fun AnswerButton(
        answer: String,
        onclick: (String) -> Unit,
        questionNumber: String,
        modifier: Modifier
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = { onclick(questionNumber) },
            shape = RoundedCornerShape(30.dp),
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .border(2.dp, Color(0, 151, 91), CircleShape)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = questionNumber,
                        fontSize = 12.sp,
                        color = Color(0, 151, 91)
                    )
                }
                Text(answer, fontSize = 14.sp,
                    color = Color(0, 151, 91),
                    modifier = Modifier.padding(horizontal = 10.dp))
            }

        }
    }

    fun checkAnswer(answer: String) {
        var txt = ""
        if (quiz.questions[currentQuestion].correctAnswer == answer) {
            correctAnswers++;
            txt = "Correct!"

        } else {
            txt = "Wrong!"
        }
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
        say(txt)

        currentQuestion++

        if (currentQuestion >= quiz.questions.size) {
            val intent = Intent(this.applicationContext, QuizSummaryActivity::class.java)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalQuestions", quiz.questions.size)
            intent.putExtra("quiz", quiz)
            startActivity(intent)
            finish()
        } else {
            setContent {
                PoQuizowaneTheme {
                    Surface(
                        color = Color(0, 151, 91),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        BackgroundImage()
                        QuestionView(quiz.questions[currentQuestion]) { answer ->
                            checkAnswer(answer)
                        }
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PoQuizowaneTheme {
            Surface(
                color = Color(0, 151, 91),
                modifier = Modifier.fillMaxSize()
            ) {
                BackgroundImage()
                val q = Question(
                    "test question",
                    "ans1", "ans2", "ans3", "ans4",
                    "A", ""
                )
                QuestionView(q) { answer ->
                    checkAnswer(answer)
                }
            }
        }
    }

}
