package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customBrandColor

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.paymentpage.ui.msdk.sample.utils.extensions.resIdByName
import com.paymentpage.ui.sample.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.color.ARGBPickerState
import com.vanpra.composematerialdialogs.color.colorChooser

@Composable
internal fun ColorPickerDialog(dialogState: MaterialDialogState, onColorSelected: (Color) -> Unit) {
    val colorFields = R.color::class.java.fields
    MaterialDialog(
        dialogState = dialogState
    ) {
        val listColor = colorFields.map {
            val resourceId = LocalContext.current.resIdByName(it.name, "color")
            colorResource(id = resourceId)
        }
        colorChooser(
            colors = listColor,
            argbPickerState = ARGBPickerState.WithoutAlphaSelector,
            onColorSelected = onColorSelected,
            waitForPositiveButton = true
        )
        MaterialDialogButtons(this).positiveButton(
            text = "Choose color",
        )
    }
}