package com.azabost.lib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class TestBreakerTest {

    class MainExtension(
        val testDispatcher: TestDispatcher
    ) : BeforeEachCallback, AfterEachCallback {

        override fun beforeEach(context: ExtensionContext?) {
            println("Setting main in ${context?.testClass} - ${context?.testMethod}")
            Dispatchers.setMain(testDispatcher)
        }

        override fun afterEach(context: ExtensionContext?) {
            println("Resetting main in ${context?.testClass} - ${context?.testMethod}")
            Dispatchers.resetMain()
        }
    }

    private val testDispatcher = UnconfinedTestDispatcher()

    @RegisterExtension
    val mainExtension = MainExtension(testDispatcher)

    private val testBreaker = TestBreaker()

    @Test
    fun `1 breaking test`() = runTest {
        testBreaker.breakTests()
    }

    @Test
    fun `2 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }

    @Test
    fun `3 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }

    @Test
    fun `4 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }

    @Test
    fun `5 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }

    @Test
    fun `6 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }

    @Test
    fun `7 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }

    @Test
    fun `8 ordinary test`() = runTest {
        testBreaker.somethingOrdinary()
    }
}