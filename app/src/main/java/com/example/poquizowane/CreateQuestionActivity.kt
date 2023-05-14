package com.example.poquizowane

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poquizowane.ui.theme.PoQuizowaneTheme

class CreateQuestionActivity : ComponentActivity() {
    lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoQuizowaneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackgroundImage()
                    Inputs()
                }
            }
        }
        quiz = intent.getSerializableExtra("EXTRA_QUIZ") as Quiz
    }

    @Composable
    fun BackgroundImage() {
        Image(
            painter = painterResource(id = R.drawable.add_question),
            contentDescription = "pic",
            contentScale = ContentScale.FillBounds
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Inputs() {
        val q = 1 + quiz.questions.size
        var description by remember { mutableStateOf("") }
        var answer1 by remember { mutableStateOf("") }
        var answer2 by remember { mutableStateOf("") }
        var answer3 by remember { mutableStateOf("") }
        var answer4 by remember { mutableStateOf("") }
        val options = listOf("correct answer", "A", "B", "C", "D")
        var correct by remember { mutableStateOf(options[0]) }
        var uploadText by remember { mutableStateOf("upload picture") }
        val white = Color.White

        fun validate(): Boolean {
            if (answer1 == "" || answer2 == "" || answer3 == "" || answer4 == "") {
                Toast.makeText(
                    applicationContext,
                    "all answers must be filled",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (description == "") {
                Toast.makeText(
                    applicationContext,
                    "description can't be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (correct == options[0]) {
                Toast.makeText(
                    applicationContext,
                    "please choose a correct answer",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            return true
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "Question #$q",
                fontSize = 40.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "description") },
                placeholder = { Text(text = "enter question content") },
                maxLines = 3,
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(30.dp),
                colors = outlinedTextFieldColors(
                    textColor = white,
                    unfocusedBorderColor = white,
                    focusedBorderColor = white,
                    focusedLabelColor = white,
                    unfocusedLabelColor = white,
                    cursorColor = white,
                    placeholderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Row {
                OutlinedTextField(
                    value = answer1,
                    onValueChange = { answer1 = it },
                    label = { Text(text = "A") },
                    placeholder = { Text(text = "answer A") },
                    singleLine = true,
                    readOnly = false,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = outlinedTextFieldColors(
                        textColor = white,
                        unfocusedBorderColor = white,
                        focusedBorderColor = white,
                        focusedLabelColor = white,
                        unfocusedLabelColor = white,
                        cursorColor = white,
                        placeholderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = answer2,
                    onValueChange = { answer2 = it },
                    label = { Text(text = "B") },
                    placeholder = { Text(text = "answer B") },
                    singleLine = true,
                    readOnly = false,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = outlinedTextFieldColors(
                        textColor = white,
                        unfocusedBorderColor = white,
                        focusedBorderColor = white,
                        focusedLabelColor = white,
                        unfocusedLabelColor = white,
                        cursorColor = white,
                        placeholderColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row {
                OutlinedTextField(
                    value = answer3,
                    onValueChange = { answer3 = it },
                    label = { Text(text = "C") },
                    placeholder = { Text(text = "answer C") },
                    singleLine = true,
                    readOnly = false,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = outlinedTextFieldColors(
                        textColor = white,
                        unfocusedBorderColor = white,
                        focusedBorderColor = white,
                        focusedLabelColor = white,
                        unfocusedLabelColor = white,
                        cursorColor = white,
                        placeholderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = answer4,
                    onValueChange = { answer4 = it },
                    label = { Text(text = "D") },
                    placeholder = { Text(text = "answer D") },
                    singleLine = true,
                    readOnly = false,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = outlinedTextFieldColors(
                        textColor = white,
                        unfocusedBorderColor = white,
                        focusedBorderColor = white,
                        focusedLabelColor = white,
                        unfocusedLabelColor = white,
                        cursorColor = white,
                        placeholderColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))
            var expanded by remember { mutableStateOf(false) }

            Row(modifier = Modifier.height(60.dp)){
                ExposedDropdownMenuBox (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        value = correct,
                        onValueChange = { correct = it },
                        placeholder = { Text(text = "correct answer") },
                        singleLine = true,
                        readOnly = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(30.dp),
                        colors = outlinedTextFieldColors(
                            textColor = white,
                            unfocusedBorderColor = white,
                            focusedBorderColor = white,
                            focusedLabelColor = white,
                            unfocusedLabelColor = white,
                            cursorColor = white,
                            placeholderColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    correct = option
                                    expanded = false
                                },
                                text = { Text(text = option) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                        //TODO: UPLOAD PIC
                    },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Text(
                        uploadText,
                        fontSize = 14.sp,
                        color = Color(0, 151, 91)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier.height(60.dp)){

                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                        if (validate()) {
                            val question = Question(description, answer1, answer2, answer3, answer4, correct)
                            quiz.addQuestion(question)
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.putExtra("EXTRA_QUIZ", quiz)
                            startActivity(intent)
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Text(
                        "save",
                        fontSize = 14.sp,
                        color = Color(0, 151, 91)
                    )
                }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(Color.White),
                        onClick = {
                            if (validate()) {
                                val question = Question(description, answer1, answer2, answer3, answer4, correct)
                                quiz.addQuestion(question)
                                val intent = Intent(applicationContext, CreateQuestionActivity::class.java)
                                intent.putExtra("EXTRA_QUIZ", quiz)
                                startActivity(intent)
                            }
                        },
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            "add more",
                            fontSize = 14.sp,
                            color = Color(0, 151, 91)
                        )
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
                Inputs()
            }
        }
    }


}