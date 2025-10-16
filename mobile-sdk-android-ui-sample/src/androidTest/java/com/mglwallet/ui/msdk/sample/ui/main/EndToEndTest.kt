@file:OptIn(ExperimentalTestApi::class)

package com.mglwallet.ui.msdk.sample.ui.main

import android.app.Instrumentation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.mglwallet.ui.msdk.sample.domain.ui.base.viewUseCase
import com.mglwallet.ui.msdk.sample.domain.ui.sample.SampleViewUC
import com.mglwallet.ui.msdk.sample.ui.sample.SampleActivity
import com.mglwallet.ui.msdk.sample.ui.sample.SampleState
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.TestTagsConstants
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalComposeUiApi::class)
internal class MainScreenTest {
    private val viewUseCase = viewUseCase("Sample") { SampleViewUC() }

    //Create activity monitor for checking lunching another activity
    private val activityMonitor: Instrumentation.ActivityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(
        PaymentActivity::class.java.name,
        null,
        false
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SampleActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        testTagsAsResourceId = true
                    },
                color = MaterialTheme.colors.background
            ) {
                composeTestRule.activity.SampleState(viewUseCase = viewUseCase)
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun endToEndTest() {
        composeTestRule
            .onNodeWithTag("paymentDescriptionTextField")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("paymentDescriptionTextField")
            .performTextInput("123")

        composeTestRule
            .onNodeWithTag("paymentDescriptionTextField")
            .performClick()
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