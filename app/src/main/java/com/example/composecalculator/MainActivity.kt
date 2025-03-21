package com.example.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.composecalculator.ui.theme.ComposeCalculatorTheme
import com.example.composecalculator.viewmodel.CalculatorViewModel
import com.example.composecalculator.viewmodel.Operation

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val display by viewModel.display.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display
        Text(
            text = display,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.End
        )

        // Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CalculatorButton("C", onClick = { viewModel.reset() })
                CalculatorButton("AC", onClick = { viewModel.clear() })
                CalculatorButton("%", onClick = { viewModel.addOperation(Operation.PERC) })
                CalculatorButton("÷", onClick = { viewModel.addOperation(Operation.DIV) })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CalculatorButton("7", onClick = { viewModel.addDigit(7) })
                CalculatorButton("8", onClick = { viewModel.addDigit(8) })
                CalculatorButton("9", onClick = { viewModel.addDigit(9) })
                CalculatorButton("×", onClick = { viewModel.addOperation(Operation.MUL) })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CalculatorButton("4", onClick = { viewModel.addDigit(4) })
                CalculatorButton("5", onClick = { viewModel.addDigit(5) })
                CalculatorButton("6", onClick = { viewModel.addDigit(6) })
                CalculatorButton("-", onClick = { viewModel.addOperation(Operation.SUB) })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CalculatorButton("1", onClick = { viewModel.addDigit(1) })
                CalculatorButton("2", onClick = { viewModel.addDigit(2) })
                CalculatorButton("3", onClick = { viewModel.addDigit(3) })
                CalculatorButton("+", onClick = { viewModel.addOperation(Operation.ADD) })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CalculatorButton("0", onClick = { viewModel.addDigit(0) })
                CalculatorButton("±", onClick = { viewModel.toggleSign() })
                CalculatorButton(".", onClick = { viewModel.addPoint() })
                CalculatorButton("=", onClick = { viewModel.compute() })
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
            .width(90.dp)
            .height(90.dp)
    ) {
        Text(text = text, fontSize = 28.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    ComposeCalculatorTheme {
        CalculatorScreen(CalculatorViewModel())
    }
}