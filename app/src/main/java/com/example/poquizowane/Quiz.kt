package com.example.poquizowane

import java.io.Serializable

data class Quiz(
    val name: String,
    val category: String,
    val difficulty: String,
    var questions: MutableList<Question>
) : Serializable {

    fun addQuestion(question: Question) {
        questions.add(question)
    }

}