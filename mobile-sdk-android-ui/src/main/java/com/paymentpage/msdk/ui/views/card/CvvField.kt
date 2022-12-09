package com.paymentpage.msdk.ui.views.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.CustomTextField
import com.paymentpage.msdk.ui.views.common.alertDialog.ConfirmAlertDialog

@Composable
internal fun CvvField(
    initialValue: String? = null,
    modifier: Modifier,
    cardType: String? = null,
    length: Int = if (cardType == "amex") 4 else 3,
    onValueChanged: (String, Boolean) -> Unit,
) {
    var cvvAlertDialogState by remember { mutableStateOf(false) }
    CustomTextField(
        initialValue = initialValue,
        modifier = modifier,
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
                modifier = Modifier.clickable(onClick = { cvvAlertDialogState = true }),
                painter = painterResource(id = R.drawable.cvv_info_icon), contentDescription = null
            )
        }
    )
    if (cvvAlertDialogState) {
        ConfirmAlertDialog(
            title = { Text(text = getStringOverride(OverridesKeys.TITLE_ABOUT_CVV)) },
            message = { Text(text = getStringOverride(OverridesKeys.MESSAGE_ABOUT_CVV)) },
            onConfirmButtonClick = { cvvAlertDialogState = false },
            confirmButtonText = getStringOverride(OverridesKeys.BUTTON_OK),
            onDismissRequest = { cvvAlertDialogState = false }
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