package com.example.poquizowane

import com.google.firebase.firestore.FirebaseFirestore

class QuizRepository(private val firestore: FirebaseFirestore) {

    fun addQuiz(quiz: Quiz, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("quizzes").add(quiz)
            .addOnSuccessListener { documentReference ->
                val quizId = documentReference.id
                val updatedQuiz = quiz.copy(id = quizId)
                documentReference.set(updatedQuiz)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener(onFailure)
            }
            .addOnFailureListener(onFailure)
    }
}