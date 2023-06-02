package com.example.poquizowane

data class User(
    val uid: String = "",
    val quizScores: MutableMap<String, Int> = mutableMapOf(),
    val email: String = ""
) {
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["uid"] = this.uid
        result["quizScores"] = this.quizScores
        result["email"] = this.email
        return result
    }
}