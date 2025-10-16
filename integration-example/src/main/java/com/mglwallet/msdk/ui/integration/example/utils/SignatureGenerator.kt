package com.mglwallet.msdk.ui.integration.example.utils

import android.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SignatureGenerator {

    fun generateSignature(paramsToSign: String, secret: String): String {
        var signature = ""

        try {
            signature = hMAC(paramsToSign, secret)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return signature
    }

    private fun hMAC(value: String, key: String): String {
        val byteKey = key.toByteArray(charset("UTF-8"))
        val algorithm = "HmacSHA512"
        val sha512HMAC = Mac.getInstance(algorithm)
        val keySpec = SecretKeySpec(byteKey, algorithm)
        sha512HMAC.init(keySpec)
        val macData = sha512HMAC.doFinal(value.toByteArray(charset("UTF-8")))
        val result = Base64.encode(macData, Base64.NO_WRAP)
        return String(result, charset("UTF-8"))
    }
}