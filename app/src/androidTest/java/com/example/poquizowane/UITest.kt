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
    val CREATETAG = "create"
    val LEADERBOARDTAG = "leaderboard"
    val LOGOUTTAG = "logout"

    val LEADERBOARDSCREENTAG = "leaderboardscreen"

    val NAMETAG = "quizname"
    val CATEGORYTAG = "category"
    val DIFFICULTYTAG = "difficulty"
    val NEXTTAG = "next"

    val DESCTAG = "description"
    val ATAG = "a"
    val BTAG = "b"
    val CTAG = "c"
    val DTAG = "d"
    val CORRECTTAG = "correct"
    val SAVETAG = "save"
    val MORETAG = "more"

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
        click(CHANGEMODETAG)
        type(EMAILTAG, mail)
        type(PASSWORDTAG, pass)
        forward()
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
        //Thread.sleep(100000)

        type(DESCTAG, "test desc")
        type(ATAG, "A")
        type(BTAG, "B")
        type(CTAG, "C")
        type(DTAG, "D")
        click(CORRECTTAG)
        forward()
        click("Bans")
        forward()
        click(MORETAG)

        type(DESCTAG, "test desc")
        type(ATAG, "A")
        type(BTAG, "B")
        type(CTAG, "C")
        type(DTAG, "D")
        click(CORRECTTAG)
        forward()
        click("Cans")
        forward()
        click(SAVETAG)

        Thread.sleep(1000)
        forward(2)
        rule.onNodeWithTag(HOMESCREENTAG).assertIsDisplayed()
        forward()
    }

}