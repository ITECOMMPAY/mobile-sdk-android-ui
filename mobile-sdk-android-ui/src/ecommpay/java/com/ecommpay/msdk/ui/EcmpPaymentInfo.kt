package com.ecommpay.msdk.ui


class EcmpPaymentInfo(
    /**
     * project (merchant) ID
     */
    val projectId: Int,
    /**
     * must be unique within the project
     */
    val paymentId: String,
    /**
     * payment amount in minor currency units
     */
    val paymentAmount: Long,
    /**
     * payment currency code according to ISO-4217 alpha-3
     */
    val paymentCurrency: String,
    /**
     * payment description (this parameter is available not only to the merchant, but also to the customer; if paymentDescription is specified in the request, it is visible to the customer in the payment form (in the dialog box containing information about the payment); if this parameter is not specified in the request, it is not visible to the customer)
     */
    var paymentDescription: String? = null,
    var customerId: String? = null,
    /**
     * customer country
     */
    var regionCode: String? = null,
    /**
     * card token
     */
    var token: String? = null,
    var languageCode: String? = null,
    var receiptData: String? = null,
    //var bankId: Int? = null,
    //var actionType: ActionType = ActionType.Sale,
    /**
     * hiding or displaying saved payment instruments in the payment form
     */
    var hideSavedWallets: Boolean = false,
    /**
     * the identifier of the payment method which is opened to the customer without an option for the customer to select another payment method. The list of codes is provided in the IDs of payment methods supported on Payment Page section
     */
    var forcePaymentMethod: EcmpPaymentMethodType? = null,

    var signature: String? = null
) {

    private val PROJECT_ID = "project_id"
    private val PAYMENT_ID = "payment_id"
    private val PAYMENT_AMOUNT = "payment_amount"
    private val PAYMENT_DESCRIPTION = "payment_description"
    private val PAYMENT_CURRENCY = "payment_currency"
    private val CUSTOMER_ID = "customer_id"
    private val REGION_CODE = "region_code"
    private val TOKEN_CODE = "account_token"
    private val LANGUAGE_CODE = "language_code"
    private val FORCE_PAYMENT_METHOD_CODE = "force_payment_method"
    private val HIDE_SAVE_WALLETS_CODE = "hide_saved_wallets"

    private fun paramsForSignature(): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        map[PROJECT_ID] = projectId
        if (paymentId.isNotEmpty()) map[PAYMENT_ID] = paymentId
        map[PAYMENT_AMOUNT] = paymentAmount
        if (!paymentDescription.isNullOrEmpty()) map[PAYMENT_DESCRIPTION] = paymentDescription!!
        map[PAYMENT_CURRENCY] = paymentCurrency
        if (!customerId.isNullOrEmpty()) map[CUSTOMER_ID] = customerId!!
        if (!regionCode.isNullOrEmpty()) map[REGION_CODE] = regionCode!!
        if (!token.isNullOrEmpty()) map[TOKEN_CODE] = token!!
        if (!languageCode.isNullOrEmpty()) map[LANGUAGE_CODE] = languageCode!!
        if (forcePaymentMethod != null) map[FORCE_PAYMENT_METHOD_CODE] = forcePaymentMethod!!.value
        map[HIDE_SAVE_WALLETS_CODE] = if (hideSavedWallets) 1 else 0
        return map
    }

    fun getParamsForSignature(): String {
        val params: Map<String, Any> = paramsForSignature()
        val keys = params.keys
        val sortedKeys = ArrayList(keys)
        sortedKeys.sort()
        val paramsStr = StringBuilder()
        for (key in sortedKeys)
            paramsStr.append(key).append(":").append(params[key]).append(";")

        return paramsStr.substring(0, paramsStr.length - 1)
    }

    companion object {
        fun create(
            projectId: Int,
            paymentId: String,
            paymentAmount: Long,
            paymentCurrency: String
        ) =
            EcmpPaymentInfo(
                projectId = projectId,
                paymentId = paymentId,
                paymentAmount = paymentAmount,
                paymentCurrency = paymentCurrency
            )
    }

}
