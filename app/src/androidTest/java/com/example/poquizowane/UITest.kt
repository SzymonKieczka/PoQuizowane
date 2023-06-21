package com.example.poquizowane

import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.espresso.Espresso
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UITest {

    @get:Rule
    val rule = createAndroidComposeRule<HomeActivity>()

    val EMAILTAG = "email"
    val PASSWORDTAG = "password"
    val CONFIRMTAG = "confirm"
    val SUBMITTAG = "submit"
    val CHANGEMODETAG = "changemode"

    val HOMESCREENTAG = "homescreen"
    val PLAYTAG = "play"
    val CREATETAG = "create"
    val LEADERBOARDTAG = "leaderboard"
    val LOGOUTTAG = "logout"

    val NAMETAG = "quizname"
    val CATEGORYTAG = "category"
    val DIFFICULTYTAG = "difficulty"
    val NEXTTAG = "next"

    val LEADERBOARDSCREENTAG = "leaderboardscreen"
    val QUIZTAG = "quiz"

    fun type(tag: String, text: String) {
        rule.onNodeWithTag(tag).performClick()
        rule.onNodeWithTag(tag).performTextInput(text)
        Espresso.closeSoftKeyboard()
        rule.mainClock.advanceTimeBy(1000)
    }

    fun click(tag: String) {
        rule.onNodeWithTag(tag).performClick()
        rule.mainClock.advanceTimeBy(1000)
    }

    fun forward(seconds: Long = 1) {
        rule.mainClock.advanceTimeBy(1000 * seconds)
    }

    @Test
    fun leaderBoard() {
        rule.mainClock.autoAdvance = false
        click(LEADERBOARDTAG)
        forward()
        rule.onNodeWithTag(LEADERBOARDSCREENTAG).assertIsDisplayed()
        Espresso.pressBack()
        forward()
    }

    @Test
    fun signUpTest() {
        val date = SimpleDateFormat("yyyy-MM-dd-HH-mm").format(Date())
        val mail = "${date}@gmail.com"
        val pass = "password"
        rule.mainClock.autoAdvance = false

        click(LOGOUTTAG)
        type(EMAILTAG, mail)
        type(PASSWORDTAG, pass)
        type(CONFIRMTAG, pass)

        click(SUBMITTAG)
        Thread.sleep(1000)
        forward(2)
        rule.onNodeWithTag(HOMESCREENTAG).assertIsDisplayed()
        forward()
    }


    @Test
    fun createTest() {
        val date = SimpleDateFormat("yyyy-MM-dd-HH-mm").format(Date())
        val name = "test quiz $date"
        rule.mainClock.autoAdvance = false

        click(CREATETAG)

        type(NAMETAG, name)
        click(CATEGORYTAG)
        forward()

        click("nature")
        forward()
        click(DIFFICULTYTAG)
        forward()
        click("medium")
        forward()
        click(NEXTTAG)
        forward()
        Thread.sleep(100000)
    }

}