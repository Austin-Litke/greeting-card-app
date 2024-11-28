package com.example.greetingcard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted; start the SecondActivity
            startSecondActivity()
        } else {
            // Permission denied
            Toast.makeText(
                this,
                "Permission denied. Cannot access the Second Activity.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onExplicitButtonClick = {
                            checkAndRequestPermission()
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

    private fun checkAndRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                "com.example.greetingcard.MSE412"
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted; start the SecondActivity
                startSecondActivity()
            }
            else -> {
                // Request the custom permission
                requestPermissionLauncher.launch("com.example.greetingcard.MSE412")
            }
        }
    }

    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
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
