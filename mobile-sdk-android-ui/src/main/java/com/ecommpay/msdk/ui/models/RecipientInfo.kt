package com.ecommpay.msdk.ui.models

class RecipientInfo(
    val walletOwner: String? = null,
    val walletId: String? = null,
    val country: String? = null,
) {
    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.RecipientInfo(
            walletOwner = walletOwner,
            walletId = walletId,
            country = country
        )
}
