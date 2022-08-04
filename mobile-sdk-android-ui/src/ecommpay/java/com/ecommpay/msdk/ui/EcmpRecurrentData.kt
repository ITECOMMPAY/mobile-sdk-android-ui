package com.ecommpay.msdk.ui

class EcmpRecurrentData(
    val register: Boolean = true,
    /**
     * Type of recurrent - R/C/U/I
     */
    val type: String? = null,
    /**
     *  Day of recurrent expiration, must be string(2) day in DD format
     */
    val expiryDay: String? = null,
    /**
     * Month of recurrent expiration, must be string(2) month in MM format
     */
    val expiryMonth: String? = null,
    /**
     *  Year of recurrent expiration, must be string(4) month in YYYY format
     */
    val expiryYear: String? = null,
    /**
     *  Period of payment - Day/Week/Month/Quarter/Year
     */
    val period: String? = null,
    /**
     * Time of recurrent payment to charge
     */
    val time: String? = null,
    /**
     * Date to start recurrent payment, must be string(10) in DD-MM-YYYY format
     */
    val startDate: String? = null,
    /**
     * Payment ID, must be unique within your project
     */
    val scheduledPaymentID: String? = null,
    /**
     * Amount of COF purchase. By default the amount of COF purchase is equal to payment amount
     */
    val amount: Long? = null,
    /**
     * Data and amount of COF purchase. By default the debit of funds is strictly fixed in time and amount
     */
    val schedule: List<EcmpRecurrentDataSchedule>? = null
)

class EcmpRecurrentDataSchedule(
    /**
     *  Date of charge in format DD-MM-YYYY
     */
    val date: String? = null,
    /**
     * Amount to charge, 1000 = 10.00
     */
    val amount: Long? = null
)