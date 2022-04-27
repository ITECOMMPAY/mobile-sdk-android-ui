package com.ecommpay.msdk.ui.base

import android.widget.Toast

sealed interface MessageUI

data class MessageAlert(
    val title: String,
    val message: String,
    val isError: Boolean = false,
) : MessageUI

data class MessageToast(
    val message: String,
    val time: Int = Toast.LENGTH_SHORT,
) : MessageUI

object MessageEmpty : MessageUI
