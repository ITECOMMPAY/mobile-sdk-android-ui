package com.ecommpay.ui.base

import android.widget.Toast

sealed interface MessageUI

data class MessageAlert(
    val title: String,
    val massage: String,
    val isError: Boolean = false,
) : MessageUI

data class MessageToast(
    val massage: String,
    val time: Int = Toast.LENGTH_SHORT,
) : MessageUI

object MessageEmpty : MessageUI
