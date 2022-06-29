@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.ecommpay.msdk.ui

import java.util.*

enum class ActionType {
    Sale, Auth, Tokenize, Verify
}

fun paymentOptions(block: PaymentOptions.() -> Unit): PaymentOptions = PaymentOptions().apply(block)

/**
 * Payment configuration
 */
class PaymentOptions {

    /**
     * project (merchant) ID
     */
    var projectId: Int = 0

    /**
     * must be unique within the project
     */
    var paymentId: String = UUID.randomUUID().toString()

    /**
     * payment amount in minor currency units
     */
    var paymentAmount: Long = 0

    /**
     * payment currency code according to ISO-4217 alpha-3
     */
    var paymentCurrency: String = ""

    var paymentDescription: String? = null
    var customerId: String? = null
    var regionCode: String? = null

    /**
     * card token
     */
    var token: String? = null
    var languageCode: String? = null
    var forcePaymentMethod: String? = null

    /**
     * hiding or displaying saved payment instruments in the payment form
     */
    var hideSavedWallets: Boolean = false
    var signature: String? = null
    var actionType: ActionType = ActionType.Sale

    var receiptData: String? = null
    var bankId: Int? = null


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
        if (!forcePaymentMethod.isNullOrEmpty()) map[FORCE_PAYMENT_METHOD_CODE] =
            forcePaymentMethod!!
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
}