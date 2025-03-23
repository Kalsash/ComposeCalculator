package com.example.composecalculator.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

class CalculatorViewModel : ViewModel(), Calculator {
    private var _display = MutableStateFlow("0")
    override var display: StateFlow<String> = _display.asStateFlow()
    private val eps = 0.000000001
    private val e = 1000000.0
    private var wasPoint: Boolean = false
    private var wasCompute: Boolean = false
    private var digit: Double = 0.0
    private var digit2: String = ""
    private var lastDigit: Double? = 0.0
    private var k: Int = 10
    private var operation: Operation? = null

    private fun formatDisplayValue(value: Double): String {
        return if (abs(value - value.toInt()) < eps) {
            "${value.toInt()}"
        } else {
            "${(kotlin.math.round(value * e) / e)}"
        }
    }

    override fun addDigit(dig: Int) {
        if (!wasCompute) {
            if (wasPoint) {
                digit2 += dig
                digit += dig / (k + 0.0)
                k *= 10
                _display.value = formatDisplayValue(digit)
            } else {
                digit = 10 * digit + dig
                _display.value = formatDisplayValue(digit)
            }
        }
    }

    override fun addPoint() {
        if (!wasCompute && !wasPoint) {
            wasPoint = true
            _display.value = "${formatDisplayValue(digit)}."
        }
    }

    override fun addOperation(op: Operation) {
        if (operation != null) {
            if (operation == op) {
                return
            }
            compute()
        }
        lastDigit = digit
        digit = 0.0
        digit2 = ""
        k = 10
        wasPoint = false
        wasCompute = false
        operation = op
    }

    override fun compute() {
        var b = true
        if (lastDigit != null) {
            when (operation) {
                Operation.ADD -> digit += lastDigit!!
                Operation.SUB -> digit = lastDigit!! - digit
                Operation.MUL -> digit *= lastDigit!!
                Operation.DIV -> {
                    if (digit != 0.0) {
                        digit = lastDigit!! / digit
                    } else {
                        _display.value = "ERROR"
                        digit = 0.0
                        b = false
                    }
                }
                Operation.PERC -> digit = lastDigit!! * (digit / 100)
                Operation.NEG -> digit *= -1
                else -> {}
            }
            lastDigit = null
            wasCompute = true
            if (b) {
                _display.value = formatDisplayValue(digit)
            }
        }
    }

    override fun toggleSign() {
        digit *= -1
        _display.value = formatDisplayValue(digit)
    }

    override fun clear() {
        val currentDisplay = _display.value

        if (currentDisplay.length > 1) {
            val newDisplay = currentDisplay.dropLast(1)
            _display.value = newDisplay

            digit = newDisplay.toDoubleOrNull() ?: 0.0
            if (newDisplay.contains(".")) {
                wasPoint = true
                digit2 = newDisplay.substringAfterLast(".")
            } else {
                wasPoint = false
                digit2 = ""
            }
        } else {
            reset()
        }
    }

    override fun reset() {
        lastDigit = null
        digit = 0.0
        digit2 = ""
        k = 10
        wasPoint = false
        wasCompute = false
        operation = null
        _display.value = "0"
    }
}