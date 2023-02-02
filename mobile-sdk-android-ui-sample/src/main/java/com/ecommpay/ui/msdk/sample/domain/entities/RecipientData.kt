package com.ecommpay.ui.msdk.sample.domain.entities

data class RecipientData(
    val walletOwner: String? = null,
    val walletId: String? = null,
    val country: String? = null,
    val pan: String? = null,
    val cardHolder: String? = null,
    val address: String? = null,
    val city: String? = null,
    val stateCode: String? = null,
) {
    companion object {
        val mockData = RecipientData(
            walletOwner = "Wallet owner",
            walletId = "Wallet id",
            country = "US",
            pan = "5555555555554444",
            cardHolder = "Test Test",
            address = "Address",
            city = "City",
            stateCode = "TX",
        )
    }
}
