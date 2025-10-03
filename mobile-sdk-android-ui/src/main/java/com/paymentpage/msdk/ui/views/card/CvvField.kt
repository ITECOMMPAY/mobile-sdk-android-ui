package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun CvvField(
    initialValue: String? = null,
    modifier: Modifier,
    shape: Shape = SDKTheme.shapes.radius16,
    length: Int,
    testTag: String? = null,
    onValueChanged: (String, Boolean) -> Unit,
    onRequestValidatorMessage: ((String) -> Pair<String?, Boolean>?)? = null,
) {

    var isError by remember { mutableStateOf(false) }

    CustomTextField(
        initialValue = initialValue,
        modifier = modifier,
        shape = shape,
        keyboardType = KeyboardType.Number,
        isError = isError,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        onRequestValidatorMessage = {
            val validatorResponse = onRequestValidatorMessage?.invoke(it)
            isError = validatorResponse?.second ?: false

            validatorResponse?.first
        },
        onValueChanged = { value, isValid ->
            onValueChanged(value, value.length == length && isValid)
        },
        visualTransformation = PasswordVisualTransformation(),
        label = getStringOverride(OverridesKeys.TITLE_CVV),
        maxLength = length,
        isRequired = true,
        testTag = testTag
    )
}

@Composable
@Preview(showBackground = true)
private fun CvvFieldPreview() {
    CvvField(
        modifier = Modifier,
        length = 3,
        onValueChanged = { _, _ -> }
    )
}