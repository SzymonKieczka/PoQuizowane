package com.example.poquizowane

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CreateQuestionActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    lateinit var quiz: Quiz
    private var tts: TextToSpeech? = null
    private var selectedImageUri: Uri? = null
    private val quizRepository = QuizRepository(FirebaseFirestore.getInstance())


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

    private fun say(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

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
        tts = TextToSpeech(this, this)
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
        var answerA by remember { mutableStateOf("") }
        var answerB by remember { mutableStateOf("") }
        var answerC by remember { mutableStateOf("") }
        var answerD by remember { mutableStateOf("") }
        val options = listOf("correct answer", "A", "B", "C", "D")
        var correct by remember { mutableStateOf(options[0]) }
        var uploadText by remember { mutableStateOf("upload picture") }
        val white = Color.White

        fun validate(): Boolean {
            if (answerA == "" || answerB == "" || answerC == "" || answerD == "") {
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

            IconButton(
                onClick = {
                    val str = "description: $description answers: a: $answerA, b: $answerB, c: $answerC, d: $answerD."
                    say(str)
                }) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.baseline_volume_up_24),
                    contentDescription = "Speak text"
                )
            }

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
                    value = answerA,
                    onValueChange = { answerA = it },
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
                    value = answerB,
                    onValueChange = { answerB = it },
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
                    value = answerC,
                    onValueChange = { answerC = it },
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
                    value = answerD,
                    onValueChange = { answerD = it },
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
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, 0)
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
                            val imageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
                            selectedImageUri?.let { uri ->
                                imageRef.putFile(uri)
                                    .addOnSuccessListener { taskSnapshot ->
                                        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                                            val question = Question(description, answerA, answerB, answerC, answerD, correct, uri.toString())
                                            quiz.addQuestion(question)

                                            val db = FirebaseFirestore.getInstance()

                                            quizRepository.addQuiz(quiz,
                                                onSuccess = {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Quiz saved successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    val intent = Intent(applicationContext, HomeActivity::class.java)
                                                    intent.putExtra("EXTRA_QUIZ", quiz)
                                                    startActivity(intent)
                                                },
                                                onFailure = { e ->
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Quiz could not be saved",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    Log.w(TAG, "Error adding document", e)
                                                }
                                            )
                                        }
                                    }
                            } ?: run {
                                val question = Question(
                                    description,
                                    answerA,
                                    answerB,
                                    answerC,
                                    answerD,
                                    correct
                                )
                                quiz.addQuestion(question)

                                quizRepository.addQuiz(quiz,
                                    onSuccess = {
                                        Toast.makeText(
                                            applicationContext,
                                            "Quiz saved successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(applicationContext, HomeActivity::class.java)
                                        intent.putExtra("EXTRA_QUIZ", quiz)
                                        startActivity(intent)
                                    },
                                    onFailure = { e ->
                                        Toast.makeText(
                                            applicationContext,
                                            "Quiz could not be saved",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.w(TAG, "Error adding document", e)
                                    }
                                )
                            }
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

                                val question = Question(description, answerA, answerB, answerC, answerD, correct)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            Toast.makeText(
                applicationContext,
                "Image succesfully picked",
                Toast.LENGTH_SHORT
            ).show()
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