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
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCardType
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.views.common.CustomTextField
import com.paymentpage.msdk.ui.views.common.alertDialog.ConfirmAlertDialog
import com.paymentpage.msdk.ui.views.common.alertDialog.SDKAlertDialog

@Composable
internal fun CvvField(
    initialValue: String? = null,
    modifier: Modifier,
    cardType: PaymentMethodCardType? = null,
    length: Int = if (cardType == PaymentMethodCardType.AMEX) 4 else 3,
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
                PaymentActivity.stringResourceManager.getStringByKey("message_invalid_cvv")
            else
                null
        },
        onValueChanged = { value, isValid ->
            onValueChanged(value, value.length == length && isValid)
        },
        visualTransformation = PasswordVisualTransformation(),
        label = PaymentActivity.stringResourceManager.getStringByKey("title_cvv"),
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
            title = { Text(text = PaymentActivity.stringResourceManager.getStringByKey("title_about_cvv")) },
            message = { Text(text = PaymentActivity.stringResourceManager.getStringByKey("message_about_cvv")) },
            onConfirmButtonClick = { cvvAlertDialogState = false },
            confirmButtonText = PaymentActivity.stringResourceManager.getStringByKey("button_ok"),
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