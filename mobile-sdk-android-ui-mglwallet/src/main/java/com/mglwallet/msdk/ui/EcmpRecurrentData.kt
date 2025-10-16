package com.mglwallet.msdk.ui

import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfoSchedule

class EcmpRecurrentData(
    register: Boolean = true,
    /**
     * Type of recurrent - R/C/U/I
     */
    type: String? = null,
    /**
     *  Day of recurrent expiration, must be string(2) day in DD format
     */
    expiryDay: String? = null,
    /**
     * Month of recurrent expiration, must be string(2) month in MM format
     */
    expiryMonth: String? = null,
    /**
     *  Year of recurrent expiration, must be string(4) month in YYYY format
     */
    expiryYear: String? = null,
    /**
     *  Period of payment - Day/Week/Month/Quarter/Year
     */
    period: String? = null,
    /**
     *  Interval of payment: 1-100
     *  (For example: period = W and interval = 3 - payment is every three weeks)
     */
    interval: Int? = null,
    /**
     * Time of recurrent payment to charge
     */
    time: String? = null,
    /**
     * Date to start recurrent payment, must be string(10) in DD-MM-YYYY format
     */
    startDate: String? = null,
    /**
     * Payment ID, must be unique within your project
     */
    scheduledPaymentID: String? = null,
    /**
     * Amount of COF purchase. By default the amount of COF purchase is equal to payment amount
     */
    amount: Long? = null,
    /**
     * Data and amount of COF purchase. By default the debit of funds is strictly fixed in time and amount
     */
    schedule: List<EcmpRecurrentDataSchedule>? = null,
) : RecurrentInfo(
    register = register,
    type = type,
    expiryDay = expiryDay,
    expiryMonth = expiryMonth,
    expiryYear = expiryYear,
    period = period,
    interval = interval,
    time = time,
    startDate = startDate,
    scheduledPaymentID = scheduledPaymentID,
    amount = amount,
    schedule = schedule
)

class EcmpRecurrentDataSchedule(
    /**
     *  Date of charge in format DD-MM-YYYY
     */
    date: String? = null,
    /**
     * Amount to charge, 1000 = 10.00
     */
    amount: Long? = null,
) : RecurrentInfoSchedule(
    date = date,
    amount = amount
)