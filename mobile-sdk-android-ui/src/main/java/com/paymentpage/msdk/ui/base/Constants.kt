package com.paymentpage.msdk.ui.base

internal object Constants {
    const val RESULT_SUCCESS = 1 //when payment completed successfully
    const val RESULT_DECLINE = 100 //when payment declined
    const val RESULT_CANCELLED = 200 //when user canceled payment flow

    const val EXTRA_PAYMENT = "payment"

    const val RESULT_ERROR = 500 //when error occurred
    const val EXTRA_ERROR_CODE = "error_code"
    const val EXTRA_ERROR_MESSAGE = "error_message"

    const val EXTRA_API_HOST = "api_host"
    const val EXTRA_WS_API_HOST = "ws_api_host"
    const val EXTRA_MOCK_MODE_TYPE = "mock_mode_type"
    const val THREE_D_SECURE_REDIRECT_DURATION = 2000L

    internal const val COUNT_OF_VISIBLE_CUSTOMER_FIELDS = 3
    internal const val GOOGLE_PAY_ACTIVITY_REQUEST_CODE = 991

    internal const val AMEX_CARD_TYPE_NAME = "amex"
    internal const val DINERS_CLUB_CARD_TYPE_NAME = "diners_club"

    //CardIO
    //common configuration
    internal const val SCAN_WITHOUT_CAMERA = false
    internal const val SUPPRESS_CONFIRMATION = true
    internal const val SUPPRESS_MANUAL_ENTRY = true
    //cardholder name configuration
    internal const val REQUIRE_CARDHOLDER_NAME = false
    //expiry configuration
    internal const val SCAN_EXPIRY = true
    internal const val REQUIRE_EXPIRY = false
    //logo configuration
    internal const val HIDE_CARDIO_LOGO = true
    internal const val SHOW_PAYPAL_LOGO_ON_ACTION_BAR = false
    //theme configuration
    internal const val USE_THEME_APP = false
}