package com.example.poquizowane

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import com.example.poquizowane.ui.theme.PoQuizowaneTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoQuizowaneTheme {
                Column (
                    modifier = Modifier.fillMaxSize().background(Color(0, 151, 91))
                ) {
                    AppName(modifier = Modifier.fillMaxSize(1.0f))
                }
            }
        }
    }
}


@Preview(showBackground = true) //, backgroundColor = 4278228827)
@Composable
fun DefaultPreview() {
    PoQuizowaneTheme {
        Column (
            modifier = Modifier.fillMaxSize().background(Color(0, 151, 91))
            //color = MaterialTheme.colorScheme.contentColorFor(Color(0, 151, 91))
        ) {
            AppName(modifier = Modifier.fillMaxSize(1.0f))
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

