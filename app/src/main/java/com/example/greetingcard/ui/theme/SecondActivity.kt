package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChallengesScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBackButtonClick = {
                            finish() // Return to the MainActivity
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ChallengesScreen(modifier: Modifier = Modifier, onBackButtonClick: () -> Unit) {
    Surface(color = Color.White) {
        Column(modifier = modifier.padding(24.dp)) {
            Text(text = "Mobile Software Engineering Challenges:", style = MaterialTheme.typography.bodyLarge) // Fixed text style
            Spacer(modifier = Modifier.height(16.dp))
            val challenges = listOf(
                "Device Fragmentation",
                "Performance Optimization",
                "Security Concerns",
                "User Experience Design",
                "Testing and Debugging"
            )
            challenges.forEach { challenge ->
                Text(text = "• $challenge", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onBackButtonClick) {
                Text(text = "Main Activity")
            }
        }
    }
}
