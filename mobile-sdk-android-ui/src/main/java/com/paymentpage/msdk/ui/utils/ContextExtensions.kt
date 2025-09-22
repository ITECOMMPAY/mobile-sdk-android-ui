package com.paymentpage.msdk.ui.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast


fun Context.copyInClipBoard(text: String, textToast: String? = null) {
    val clipboard: ClipboardManager =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("", text)
    clipboard.setPrimaryClip(clip)

    textToast?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}