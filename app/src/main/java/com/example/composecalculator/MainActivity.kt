package com.example.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(viewModel: Calculator) {
    val display by viewModel.display.collectAsState()
    Text(text = display)
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    ComposeCalculatorTheme {
        CalculatorScreen(CalculatorViewModel())
    }
}