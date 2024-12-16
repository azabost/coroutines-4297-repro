package com.azabost.lib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestBreaker : ViewModel() {

    val number = MutableStateFlow(0)

    fun breakTests() {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(10) {
                withContext(Dispatchers.Main) {
                    println("Updating: +1")
                    number.update { it + 1 }
                }
            }
        }
    }

    fun somethingOrdinary() {
        viewModelScope.launch {
            emitAndObserve()
        }
    }

    private suspend fun emitAndObserve() = coroutineScope {
        launch {
            number
                .take(2)
                .onEach { println("Collected $it") }
        }
        launch {
            repeat(10) {
                println("Updating: x2")
                number.update { it * 2 }
            }
        }
    }
}