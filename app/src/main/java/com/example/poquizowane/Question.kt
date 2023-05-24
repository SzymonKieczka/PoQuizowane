package com.example.poquizowane

import androidx.versionedparcelable.VersionedParcelize
import java.io.Serializable

@VersionedParcelize
data class Question @JvmOverloads constructor(
    val description: String = "",
    val answerA: String = "",
    val answerB: String = "",
    val answerC: String = "",
    val answerD: String = "",
    val correctAnswer: String = "",
    val imageUrl: String? = null
) : Serializable {

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["description"] = this.description
        result["answerA"] = this.answerA
        result["answerB"] = this.answerB
        result["answerC"] = this.answerC
        result["answerD"] = this.answerD
        result["correctAnswer"] = this.correctAnswer
        result["imageUrl"] = this.imageUrl ?: ""
        return result
    }

}