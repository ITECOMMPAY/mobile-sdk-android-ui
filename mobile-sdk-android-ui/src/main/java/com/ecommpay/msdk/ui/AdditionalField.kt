package com.ecommpay.msdk.ui

@PaymentOptionsDsl
class AdditionalField {
    var type: AdditionalFieldType = AdditionalFieldType.UNKNOWN
    var value: String = ""
}

@PaymentOptionsDsl
class AdditionalFields : ArrayList<AdditionalField>() {

    fun field(block: AdditionalField.() -> Unit) {
        add(AdditionalField().apply(block))
    }

}

enum class AdditionalFieldType(val value: String) {
    UNKNOWN(""),
    CUSTOMER_EMAIL("email"),
    CUSTOMER_ZIP("zip"),
    CUSTOMER_POST_CODE("avs_data.avs_post_code"),
    CUSTOMER_ADDRESS("address"),
    CUSTOMER_STREET_ADDRESS("avs_data.avs_street_address"),
    CUSTOMER_COUNTRY("country"),
    CUSTOMER_FIRST_NAME("first_name"),
    CUSTOMER_MIDDLE_NAME("middle_name"),
    CUSTOMER_LAST_NAME("last_name"),
    CUSTOMER_PHONE("phone"),
    CUSTOMER_STATE("state"),
    CUSTOMER_CITY("city"),
    CUSTOMER_DAY_OF_BIRTH("day_of_birth"),
    CUSTOMER_BIRTH_PLACE("birthplace"),
    CUSTOMER_SSN("ssn"),
    CUSTOMER_DOMAIN("domain"),
    CUSTOMER_MIR("mir"),
    CUSTOMER_ACCOUNT_ID("account_id"),
    CUSTOMER_LANGUAGE("language"),
    CUSTOMER_SCREEN_RES("screen_res"),
    CUSTOMER_SAVE("save"),
    BILLING_POSTAL("billing_postal"),
    BILLING_COUNTRY("billing_country"),
    BILLING_REGION("billing_region"),
    BILLING_CITY("billing_city"),
    BILLING_ADDRESS("billing_address"),
    IDENTIFY_DOC_NUMBER("identify_doc_number"),
    IDENTIFY_DOC_TYPE("identify_doc_type"),
    IDENTIFY_DOC_ISSUE_DATE("identify_doc_issue_date"),
    IDENTIFY_DOC_ISSUE_BY("identify_doc_issue_by"),
    CUSTOM("CUSTOM"),
    DOKU_CUSTOMER_EMAIL("doku_customer_email"),
    DOKU_CUSTOMER_FIRST_NAME("doku_customer_first_name"),
    QIWI_ACCOUNT_NUMBER("qiwi_account_number"),
    NETELLER_ACCOUNT_NUMBER("neteller_account_number"),
    NETELLER_SECURITY_CODE("neteller_security_code"),
    WEBMONEY_ACCOUNT_TYPE("webmoney_account_type"),
    WEBMONEY_ACCOUNT_NUMBER("webmoney_account_number");

}