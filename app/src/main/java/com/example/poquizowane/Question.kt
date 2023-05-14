package com.example.poquizowane

import java.io.Serializable

data class Question(
    val description: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: String,  // "A", "B", "C", "D"
    //TODO: IMAGE
) : Serializable {

}