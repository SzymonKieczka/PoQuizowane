package com.example.poquizowane

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
            if (question.imageUrl != null && question.imageUrl != "") {
                AsyncImage(
                    model = question.imageUrl,
                    contentDescription = question.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )
            }

            Surface(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp
            ) {
                Text(
                    text = question.description,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Row {
                Column {
                    AnswerButton(question.answerA, onAnswerClick, "A")
                    AnswerButton(question.answerB, onAnswerClick, "C")
                }
                Column {
                    AnswerButton(question.answerC, onAnswerClick, "B")
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
                .fillMaxWidth()
                .padding(8.dp)
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
