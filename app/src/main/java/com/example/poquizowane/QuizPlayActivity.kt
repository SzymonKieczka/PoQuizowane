package com.example.poquizowane

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.poquizowane.ui.theme.PoQuizowaneTheme

class QuizPlayActivity : ComponentActivity() {
    var currentQuestion = 0
    var correctAnswers = 0
    lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quiz = intent.getSerializableExtra("quiz") as Quiz

        setContent {
            PoQuizowaneTheme {
                Surface(
                    color = Color(0, 151, 91)
                ) {
                    QuestionView(quiz.questions[currentQuestion]) { answer ->
                        checkAnswer(answer)
                    }
                }
            }
        }
    }

    @Composable
    fun QuestionView(question: Question, onAnswerClick: (String) -> Unit) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Question ${currentQuestion + 1}/${quiz.questions.size}",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
            if (question.imageUrl != null && question.imageUrl != "") {
                AsyncImage(
                    model = question.imageUrl,
                    contentDescription = question.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(maxOf(300.dp))
                        .padding(horizontal = 10.dp)
                )
            }

            Surface(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                color = Color(0, 151, 91),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = question.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Row {
                Column {
                    AnswerButton(question.answerA, onAnswerClick, "A")
                    AnswerButton(question.answerB, onAnswerClick, "B")
                }
                Column {
                    AnswerButton(question.answerC, onAnswerClick, "C")
                    AnswerButton(question.answerD, onAnswerClick, "D")
                }
            }
        }
    }

    @Composable
    fun AnswerButton(answer: String, onClick: (String) -> Unit, questionNumber: String) {
        Button(
            onClick = { onClick(questionNumber) },
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp),
            colors = ButtonDefaults.buttonColors( Color(0, 151, 91)),
            border = BorderStroke(1.dp, Color.Black)

        ) {
            Text(text = answer)
        }
    }

    fun checkAnswer(answer: String) {
        if (quiz.questions[currentQuestion].correctAnswer == answer) {
            correctAnswers++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
        }

        currentQuestion++

        if (currentQuestion >= quiz.questions.size) {
            val intent = Intent(this.applicationContext, QuizSummaryActivity::class.java)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalQuestions", quiz.questions.size)
            startActivity(intent)
            finish()
        } else {
            setContent {
                PoQuizowaneTheme {
                    Surface(
                        color = Color(0, 151, 91)
                    ) {
                        QuestionView(quiz.questions[currentQuestion]) { answer ->
                            checkAnswer(answer)
                        }
                    }
                }
            }
        }
    }
}
