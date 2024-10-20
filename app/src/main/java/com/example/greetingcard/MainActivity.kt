package com.example.greetingcard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onExplicitButtonClick = {
                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                        },
                        onImplicitButtonClick = {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Sharing from the app")
                                type = "text/plain"
                            }
                            startActivity(Intent.createChooser(intent, "Share with"))
                        },
                        onViewImageActivityClick = {
                            val intent = Intent(this, ImageCaptureActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onExplicitButtonClick: () -> Unit,
    onImplicitButtonClick: () -> Unit,
    onViewImageActivityClick: () -> Unit
) {
    Surface {
        Column(modifier = modifier.padding(24.dp)) {
            Text(text = "Hi, my name is Austin! Student ID: 1413857")
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onExplicitButtonClick) {
                Text(text = "Start Activity Explicitly")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onImplicitButtonClick) {
                Text(text = "Start Activity Implicitly")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onViewImageActivityClick) {
                Text(text = "View Image Activity")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    GreetingCardTheme {
        MainScreen(onExplicitButtonClick = {}, onImplicitButtonClick = {}, onViewImageActivityClick = {})
    }
}
