package com.example.poquizowane

import AuthViewModel
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInSignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        authViewModel = AuthViewModel()
    }

    @Test
    fun checkEmailTextFieldDisplayed() {
        composeTestRule.setContent {
            SignInSignUpScreen(authViewModel = AuthViewModel(), isTesting = true)
        }

        composeTestRule.onNodeWithTag("email")
            .assertIsDisplayed()
    }

    @Test
    fun checkPasswordTextFieldDisplayed() {
        composeTestRule.setContent {
            SignInSignUpScreen(authViewModel = AuthViewModel(), isTesting = true)
        }

        composeTestRule.onNodeWithTag("password")
            .assertIsDisplayed()
    }

    @Test
    fun checkConfirmPasswordTextFieldNotDisplayedInitially() {
        composeTestRule.setContent {
            SignInSignUpScreen(authViewModel = AuthViewModel(), isTesting = true)
        }

        composeTestRule.onNodeWithTag("confirm")
            .assertDoesNotExist()
    }

    @Test
    fun checkSignUpButtonTogglesConfirmPasswordVisibility() {
        composeTestRule.setContent {
            SignInSignUpScreen(authViewModel = AuthViewModel(), isTesting = true)
        }

        composeTestRule.onNodeWithTag("changemode")
            .performClick()

        composeTestRule.onNodeWithTag("confirm")
            .assertIsDisplayed()
    }
}
