package com.ecommpay.msdk.ui

enum class EcmpPaymentMethodType(val value: String) {
    CARD("card"),
    NETELLER_WALLET("neteller-wallet"),
    QIWI("qiwi"),
    SKRILL_WALLET("skrill"),
    DOKU("doku"),
    MCASH("mcash"),
    BOOST("boost"),
    BIGC("bigccash"),
    ALIPAY("alipay"),
    QIWI_KZ("qiwi-kz"),
    ATF24("atf24"),
    WEBMONEY_LIGHT("webmoney-light"),
    WEBMONEY_CLASSIC("webmoney"),
    GOOGLE_PAY("google_pay_host"),
    APPLE_PAY("apple_pay_core");
}