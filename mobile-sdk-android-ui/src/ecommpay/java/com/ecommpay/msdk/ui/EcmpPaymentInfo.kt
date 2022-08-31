package com.ecommpay.msdk.ui

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureInfo
import com.paymentpage.msdk.core.domain.entities.PaymentInfo


class EcmpPaymentInfo(
    /**
     * project (merchant) ID
     */
    projectId: Int,
    /**
     * must be unique within the project
     */
    paymentId: String,
    /**
     * payment amount in minor currency units
     */
    paymentAmount: Long,
    /**
     * payment currency code according to ISO-4217 alpha-3
     */
    paymentCurrency: String,
    /**
     * payment description (this parameter is available not only to the merchant, but also to the customer; if paymentDescription is specified in the request, it is visible to the customer in the payment form (in the dialog box containing information about the payment); if this parameter is not specified in the request, it is not visible to the customer)
     */
    paymentDescription: String? = null,
    customerId: String? = null,
    /**
     * customer country
     */
    regionCode: String? = null,
    /**
     * card token
     */
    token: String? = null,
    languageCode: String? = null,
    receiptData: String? = null,
    ecmpThreeDSecureInfo: EcmpThreeDSecureInfo? = null,
    //var bankId: Int? = null,
    //var actionType: ActionType = ActionType.Sale,
    /**
     * hiding or displaying saved payment instruments in the payment form
     */
    hideSavedWallets: Boolean = false,
    /**
     * the identifier of the payment method which is opened to the customer without an option for the customer to select another payment method. The list of codes is provided in the IDs of payment methods supported on Payment Page section
     */
    forcePaymentMethod: String? = null,

    signature: String? = null,

    ) : PaymentInfo(
    projectId = projectId,
    paymentId = paymentId,
    paymentAmount = paymentAmount,
    paymentCurrency = paymentCurrency,
    paymentDescription = paymentDescription,
    customerId = customerId,
    regionCode = regionCode,
    token = token,
    languageCode = languageCode,
    receiptData = receiptData,
    threeDSecureInfo = ecmpThreeDSecureInfo,
    hideSavedWallets = hideSavedWallets,
    forcePaymentMethod = forcePaymentMethod,
    signature = signature
)
