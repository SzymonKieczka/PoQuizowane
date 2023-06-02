package com.example.poquizowane

import androidx.versionedparcelable.VersionedParcelize
import java.io.Serializable

@VersionedParcelize
data class Quiz @JvmOverloads constructor(
    val id: String? = "",
    val name: String = "",
    val category: String = "",
    val difficulty: String = "",
    var questions: MutableList<Question> = mutableListOf()
) : Serializable {

    fun addQuestion(question: Question) {
        questions.add(question)
    }

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["name"] = this.name
        result["category"] = this.category
        result["difficulty"] = this.difficulty
        result["questions"] = this.questions.map { it.toMap() }
        return result
    }

}