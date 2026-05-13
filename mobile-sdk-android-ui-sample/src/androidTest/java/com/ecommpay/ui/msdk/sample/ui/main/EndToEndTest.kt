@file:OptIn(ExperimentalTestApi::class)

package com.ecommpay.ui.msdk.sample.ui.main

import android.app.Instrumentation
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.ecommpay.msdk.ui.Ecommpay
import com.ecommpay.ui.msdk.sample.data.ProcessRepository
import com.ecommpay.ui.msdk.sample.ui.sample.SampleActivity
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.TestTagsConstants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalComposeUiApi::class)
internal class MainScreenTest {
    //Create activity monitor for checking lunching another activity
    private val activityMonitor: Instrumentation.ActivityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(
        PaymentActivity::class.java.name,
        null,
        false
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SampleActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun endToEndTest() {
        val initialMockModeType = ProcessRepository.mockModeType
        ProcessRepository.mockModeType = Ecommpay.EcmpMockModeType.SUCCESS
        try {
            composeTestRule
                .onNodeWithTag("paymentDescriptionTextField")
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithTag("paymentDescriptionTextField")
                .performTextReplacement("Test 123")

            composeTestRule
                .onNodeWithTag("paymentDescriptionTextField")
                .assertTextEquals("Payment Description","Test 123")

            composeTestRule
                .onNodeWithTag("saleButton")
                .performScrollTo()
                .assertIsDisplayed()
                .performClick()

            val paymentActivity = activityMonitor.waitForActivityWithTimeout(5000)

            assertEquals(PaymentActivity::class.java.name, paymentActivity.javaClass.name)

            composeTestRule.waitUntilExists(
                matcher = hasTestTag(TestTagsConstants.PAYMENT_DETAILS_BUTTON),
                timeoutMillis = 10000
            )

            composeTestRule
                .onNodeWithTag(TestTagsConstants.PAYMENT_DETAILS_BUTTON)
                .performClick()

            composeTestRule.waitUntilExists(
                matcher = hasTestTag(TestTagsConstants.PAYMENT_DESCRIPTION_VALUE_TEXT),
                timeoutMillis = 10000
            )

            composeTestRule
                .onNodeWithTag(TestTagsConstants.PAYMENT_DESCRIPTION_VALUE_TEXT)
                .assertTextEquals("Test 123")

            composeTestRule
                .onNodeWithTag(TestTagsConstants.PAYMENT_DETAILS_BUTTON)
                .performClick()

            composeTestRule.waitUntilDoesNotExist(
                matcher = hasTestTag(TestTagsConstants.PAYMENT_DESCRIPTION_VALUE_TEXT),
                timeoutMillis = 10000
            )
        } finally {
            ProcessRepository.mockModeType = initialMockModeType
        }
    }

    @Test
    fun forceCloseActivePaymentSession_shouldReturnCancelledResultToHost() {
        composeTestRule.runOnUiThread {
            composeTestRule.activity.startPaymentPage()
        }

        val paymentActivity = activityMonitor.waitForActivityWithTimeout(5000)
        assertEquals(PaymentActivity::class.java.name, paymentActivity.javaClass.name)

        var isClosed = false
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            isClosed = Ecommpay.closeActivePaymentSession()
            isClosed
        }
        assertTrue("Expected PaymentActivity to be closed by SDK API", isClosed)

        composeTestRule.waitUntilExists(
            matcher = hasText("You cancelled the payment"),
            timeoutMillis = 10000
        )
    }
}

fun ComposeContentTestRule.waitUntilNodeCount(
    matcher: SemanticsMatcher,
    count: Int,
    timeoutMillis: Long = 1_000L
) {
    this.waitUntil(timeoutMillis) {
        this.onAllNodes(matcher).fetchSemanticsNodes().size == count
    }
}

fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 1_000L
) {
    return this.waitUntilNodeCount(matcher, 1, timeoutMillis)
}

fun ComposeContentTestRule.waitUntilDoesNotExist(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 1_000L
) {
    return this.waitUntilNodeCount(matcher, 0, timeoutMillis)
}
