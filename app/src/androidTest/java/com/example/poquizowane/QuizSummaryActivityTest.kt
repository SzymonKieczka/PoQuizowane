package com.example.poquizowane

import org.junit.Assert
import org.junit.Test

class QuizSummaryActivityTest {

    @Test
    fun testCalculateScorePercent_AllCorrect() {
        val scorePercent = calculateScorePercent(10, 10)
        Assert.assertEquals(100, scorePercent)
    }

    @Test
    fun testCalculateScorePercent_HalfCorrect() {
        val scorePercent = calculateScorePercent(5, 10)
        Assert.assertEquals(50, scorePercent)
    }

    @Test
    fun testCalculateScorePercent_NoneCorrect() {
        val scorePercent = calculateScorePercent(0, 10)
        Assert.assertEquals(0, scorePercent)
    }

    @Test(expected = ArithmeticException::class)
    fun testCalculateScorePercent_DivisionByZero() {
        calculateScorePercent(5, 0)
    }
}
