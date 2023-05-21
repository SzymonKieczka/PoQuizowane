package com.example.poquizowane

import androidx.versionedparcelable.VersionedParcelize
import java.io.Serializable

@VersionedParcelize
data class Question @JvmOverloads constructor(
    val description: String = "",
    val answer1: String = "",
    val answer2: String = "",
    val answer3: String = "",
    val answer4: String = "",
    val correctAnswer: String = "",
    val imageUrl: String? = null
) : Serializable {

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["description"] = this.description
        result["answer1"] = this.answer1
        result["answer2"] = this.answer2
        result["answer3"] = this.answer3
        result["answer4"] = this.answer4
        result["correct"] = this.correctAnswer
        result["imageUrl"] = this.imageUrl ?: ""
        return result
    }

}