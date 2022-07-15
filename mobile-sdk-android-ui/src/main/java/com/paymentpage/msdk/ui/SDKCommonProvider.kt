package com.paymentpage.msdk.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo

internal val LocalPaymentInfo =
    staticCompositionLocalOf<PaymentInfo> { error("No PaymentInfo found!") }
internal val LocalRecurrentInfo = staticCompositionLocalOf<RecurrentInfo?> { null }
internal val LocalThreeDSecureInfo = staticCompositionLocalOf<ThreeDSecureInfo?> { null }
internal val LocalRecipientInfo = staticCompositionLocalOf<RecipientInfo?> { null }
internal val LocalAdditionalFields = staticCompositionLocalOf<List<com.paymentpage.msdk.ui.AdditionalField>> { emptyList() }

internal val LocalMsdkSession =
    staticCompositionLocalOf<MSDKCoreSession> { error("No MSDKCoreSession found!") }


@Composable
internal fun SDKCommonProvider(
    paymentInfo: PaymentInfo,
    recurrentInfo: RecurrentInfo?,
    threeDSecureInfo: ThreeDSecureInfo?,
    recipientInfo: RecipientInfo?,
    additionalFields: List<com.paymentpage.msdk.ui.AdditionalField>,
    msdkSession: MSDKCoreSession,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalPaymentInfo provides paymentInfo,
        LocalRecurrentInfo provides recurrentInfo,
        LocalThreeDSecureInfo provides threeDSecureInfo,
        LocalRecipientInfo provides recipientInfo,
        LocalAdditionalFields provides additionalFields,
        LocalMsdkSession provides msdkSession
    ) {
        content()
    }
}