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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poquizowane.ui.theme.PoQuizowaneTheme

class CreateQuizActivity : ComponentActivity() {

    val NAMETAG = "quizname"
    val CATEGORYTAG = "category"
    val DIFFICULTYTAG = "difficulty"
    val NEXTTAG = "next"

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
    }

    @Composable
    fun BackgroundImage() {
        Image(
            painter = painterResource(id = R.drawable.create_quiz),
            contentDescription = "pic",
            contentScale = ContentScale.FillBounds
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Inputs() {
        var name by remember { mutableStateOf("") }
        val white = Color.White

        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "Create your own",
                fontSize = 32.sp,
                color = Color.White
            )
            Text(
                text = "Quiz!",
                fontSize = 40.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "name") },
                placeholder = { Text(text = "enter quiz name") },
                singleLine = true,
                readOnly = false,
                modifier = Modifier.fillMaxWidth(1f).testTag(NAMETAG),
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

            var categoryExpanded by remember { mutableStateOf(false) }
            val categoryOptions = listOf("nature", "science", "cosmos", "geography")
            var category by remember { mutableStateOf(categoryOptions[0]) }

            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = {
                    categoryExpanded = !categoryExpanded
                }, modifier = Modifier.testTag(CATEGORYTAG)
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    readOnly = true,
                    label = { Text(text = "category") },
                    placeholder = { Text(text = "enter quiz category") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
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
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categoryOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                category = option
                                categoryExpanded = false
                            },
                            text = { Text(text = option) },
                            modifier = Modifier.testTag(option)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            var difficultyExpanded by remember { mutableStateOf(false) }
            val difficultyOptions = listOf("easy", "medium", "hard")
            var difficulty by remember { mutableStateOf(difficultyOptions[0]) }

            ExposedDropdownMenuBox(
                expanded = difficultyExpanded,
                onExpandedChange = {
                    difficultyExpanded = !difficultyExpanded
                }, modifier = Modifier.testTag(DIFFICULTYTAG)
            ) {
                OutlinedTextField(
                    value = difficulty,
                    onValueChange = { difficulty = it },
                    readOnly = true,
                    label = { Text(text = "difficulty") },
                    placeholder = { Text(text = "enter quiz difficulty") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
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
                    expanded = difficultyExpanded,
                    onDismissRequest = { difficultyExpanded = false }
                ) {
                    difficultyOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                difficulty = option
                                difficultyExpanded = false
                            },
                            text = { Text(text = option) },
                            modifier = Modifier.testTag(option)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                colors = ButtonDefaults.buttonColors(white),
                onClick = {
                    if (name == "") {
                        Toast.makeText(
                            applicationContext,
                            "name can't be empty!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val intent = Intent(applicationContext, CreateQuestionActivity::class.java)
                        val quiz = Quiz(null, name, category, difficulty, mutableListOf<Question>())
                        intent.putExtra("EXTRA_QUIZ", quiz)
                        startActivity(intent)
                    }
                },
                shape = CircleShape,
                modifier = Modifier.size(60.dp).testTag(NEXTTAG)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "next",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0, 151, 91)
                )
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