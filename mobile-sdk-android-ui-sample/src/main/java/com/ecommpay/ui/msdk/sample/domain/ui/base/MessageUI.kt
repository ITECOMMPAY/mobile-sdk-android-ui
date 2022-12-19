package com.ecommpay.ui.msdk.sample.domain.ui.base

import com.ecommpay.ui.msdk.sample.R

sealed interface MessageUI {

    data class Toast(
        val message: String,
        val time: TimeMessage = TimeMessage.SHORT
    ) : MessageUI

    object Empty : MessageUI

    sealed interface Dialogs : MessageUI {

        data class CancelYes(
            val message: String,
            val listener: (Boolean) -> Unit
        ) : Dialogs

        sealed class Info(
            open val iconID: Int,
            open val title: String,
            open val message: String,
            open val buttonText: String
        ) : Dialogs {

            data class Custom(
                override val iconID: Int,
                override val title: String,
                override val message: String,
                override val buttonText: String
            ) : Info(iconID, title, message, buttonText)

            data class Success(
                override val message: String
            ) : Info(R.drawable.success, "Success", message, "OK")

            data class SuccessTokenize(
                override val message: String
            ) : Info(R.drawable.success, "Success tokenization", message, "OK")

            data class Cancelled(
                override val message: String
            ) : Info(R.drawable.warning, "Cancelled", message, "OK")

            data class Decline(
                override val message: String
            ) : Info(R.drawable.error, "Decline", message, "OK")

            data class Error(
                override val message: String
            ) : Info(R.drawable.error, "Error", message, "OK")

        }
    }
}

enum class TimeMessage(val time: Long) {
    LONG(2500),
    SHORT(1500)
}