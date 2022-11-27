package com.paymentpage.ui.msdk.sample.utils

import android.util.Base64
import java.lang.Exception
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SignatureGenerator {

    fun generateSignature(paramsToSign: String, secret: String): String {
        var signature = ""

        try {
            signature = hmac(paramsToSign, secret);
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return signature
    }

    fun hmac(value: String, key: String): String {
        val byteKey = key.toByteArray(charset("UTF-8"))
        val HMAC_SHA512 = "HmacSHA512"
        val sha512HMAC = Mac.getInstance(HMAC_SHA512)
        val keySpec = SecretKeySpec(byteKey, HMAC_SHA512)
        sha512HMAC.init(keySpec)
        val mac_data = sha512HMAC.doFinal(value.toByteArray(charset("UTF-8")))
        val result = Base64.encode(mac_data, Base64.NO_WRAP);
        return String(result, charset("UTF-8"));
    }
}
