package com.example.poquizowane

import com.google.firebase.firestore.FirebaseFirestore
class UserRepository(private val firestore: FirebaseFirestore) {

    fun addUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("users").document(user.uid).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener(onFailure)
    }

    fun getUser(uid: String, onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                user?.let(onSuccess) ?: onFailure(Exception("User not found"))
            }
            .addOnFailureListener(onFailure)
    }

    fun updateUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("users").document(user.uid).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener(onFailure)
    }

    fun updateQuizScore(user: User, quizId: String, newScore: Int, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val previousScore = user.quizScores[quizId]
        if (previousScore == null || newScore > previousScore) {
            val updatedUser = user.copy(
                quizScores = user.quizScores.apply { put(quizId, newScore) }
            )
            updateUser(updatedUser, onSuccess, onFailure)
        }
    }
}