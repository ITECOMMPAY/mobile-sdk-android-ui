package com.paymentpage.msdk.ui.views.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.CustomTextField
import com.paymentpage.msdk.ui.views.common.alertDialog.ConfirmAlertDialog

@Composable
internal fun CvvField(
    initialValue: String? = null,
    modifier: Modifier,
    shape: Shape = SDKTheme.shapes.radius16,
    cardType: String? = null,
    length: Int = if (cardType == "amex") 4 else 3,
    testTag: String? = null,
    onValueChanged: (String, Boolean) -> Unit,
) {
    val paymentOptions = LocalPaymentOptions.current
    var cvvAlertDialogState by remember { mutableStateOf(false) }
    CustomTextField(
        initialValue = initialValue,
        modifier = modifier,
        shape = shape,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        onRequestValidatorMessage = {
            if (it.length != length)
                getStringOverride(OverridesKeys.MESSAGE_INVALID_CVV)
            else
                null
        },
        onValueChanged = { value, isValid ->
            onValueChanged(value, value.length == length && isValid)
        },
        visualTransformation = PasswordVisualTransformation(),
        label = getStringOverride(OverridesKeys.TITLE_CVV),
        maxLength = length,
        isRequired = true,
        trailingIcon = {
            Image(
                modifier = Modifier
                    .clickable(onClick = { cvvAlertDialogState = true })
                    .semantics {
                        contentDescription = getStringOverride(OverridesKeys.MESSAGE_ABOUT_CVV)
                        role = Role.Button
                    }
                    .testTag(TestTagsConstants.CVV_INFO_BUTTON),
                painter = painterResource(id = SDKTheme.images.cvvInfoLogo),
                contentDescription = getStringOverride(OverridesKeys.TITLE_ABOUT_CVV)
            )
        },
        testTag = testTag
    )
    if (cvvAlertDialogState) {
        ConfirmAlertDialog(
            title = getStringOverride(OverridesKeys.TITLE_ABOUT_CVV),
            message = getStringOverride(OverridesKeys.MESSAGE_ABOUT_CVV),
            onConfirmButtonClick = { cvvAlertDialogState = false },
            confirmButtonText = getStringOverride(OverridesKeys.BUTTON_OK),
            onDismissRequest = { cvvAlertDialogState = false },
            brandColor = paymentOptions.primaryBrandColor
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CvvFieldPreview() {
    CvvField(
        modifier = Modifier,
        onValueChanged = { _, _ -> }
    )
}