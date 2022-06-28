package com.ecommpay.msdk.ui.utils.extensions

import android.content.Context
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.ecommpay.msdk.ui.BuildConfig
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.base.mvi.TimeMachine
import com.ecommpay.msdk.ui.base.mvi.UiState


internal fun Modifier.debugInputPointer(
    context: Context,
    timeTravel: TimeMachine<out UiState>,
): Modifier {
    return if (BuildConfig.IS_TIME_TRAVEL) {
        this.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    showDebugAlertDialog(context, timeTravel)
                }
            )
        }
    } else this
}

private fun showDebugAlertDialog(
    context: Context,
    timeTravel: TimeMachine<out UiState>,
) {
    val alertDialogBuilder = AlertDialog.Builder(context, R.style.DebugDialogTheme)
    val adapter = ArrayAdapter<DebugState>(context, android.R.layout.simple_list_item_1)
    adapter.addAll(timeTravel.getStates().mapIndexed(::DebugState))
    alertDialogBuilder.setAdapter(adapter) { _, which ->
        timeTravel.selectState(which)
    }

    alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    alertDialogBuilder.setPositiveButton("Ok") { dialog, _ ->
        dialog.dismiss()
    }

    val dialog = alertDialogBuilder.create()
    dialog.show()
}

private data class DebugState(val index: Int, val state: UiState) {
    override fun toString(): String {
        return "${index + 1}. $state"
    }
}