package com.paymentpage.ui.msdk.example

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ecommpay.msdk.ui.*
import com.paymentpage.ui.msdk.example.utils.CommonUtils
import com.paymentpage.ui.msdk.example.utils.SignatureGenerator

class XmlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml)
        //1. Create EcmpPaymentInfo object
        val ecmpPaymentInfo = EcmpPaymentInfo(
            projectId = 123, //Unique project Id
            paymentId = CommonUtils.getRandomPaymentId(),
            paymentAmount = 100, //1.00
            paymentCurrency = "USD",
//            paymentDescription = "",
//            customerId = "",
//            regionCode = "",
//            token = "",
//            languageCode = "",
//            receiptData = "",
//            hideSavedWallets = false,
//            forcePaymentMethod = EcmpPaymentMethodType.CARD
        )
        //2. Sign it
        ecmpPaymentInfo.signature = SignatureGenerator.generateSignature(
            paramsToSign = ecmpPaymentInfo.getParamsForSignature(),
            secret = "YOUR SECRET"
        )
        //3. Configure SDK
        val paymentOptions = paymentOptions {
            brandColor = "#000000" //#RRGGBB
            logoImage = BitmapFactory.decodeResource(resources, R.drawable.example_logo) //Any bitmap image
            paymentInfo = ecmpPaymentInfo
//            actionType = EcmpActionType.Sale
//            isTestEnvironment = true
//            merchantId = ""
//            merchantName = ""
//            recurrentData = EcmpRecurrentData()
//            recipientInfo = RecipientInfo()
//            threeDSecureInfo = ThreeDSecureInfo(
//                threeDSecureCustomerInfo = ThreeDSecureCustomerInfo(),
//                threeDSecurePaymentInfo = ThreeDSecurePaymentInfo()
//            )
//            additionalFields = EcmpAdditionalFields()
        }
        //4. Create sdk object
        val sdk = PaymentSDK(
            context = applicationContext,
            paymentOptions = paymentOptions,
            isMockModeEnabled = true, // for testing
        )
        //5. Open it
        sdk.openPaymentScreen(this, 1234)
    }
    //6. Handle result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            PaymentSDK.RESULT_SUCCESS -> {
                Toast.makeText(this, "Payment was finished successfully", Toast.LENGTH_SHORT).show()
                Log.d("PaymentSDK", "Payment was finished successfully")
            }
            PaymentSDK.RESULT_CANCELLED -> {
                Toast.makeText(this, "Payment was cancelled", Toast.LENGTH_SHORT).show()
                Log.d("PaymentSDK", "Payment was cancelled")
            }
            PaymentSDK.RESULT_DECLINE -> {
                Toast.makeText(this, "Payment was declined", Toast.LENGTH_SHORT).show()
                Log.d("PaymentSDK", "Payment was declined")
            }
            PaymentSDK.RESULT_ERROR -> {
                val errorCode = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
                val message = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
                Toast.makeText(this, "Payment was interrupted. See logs", Toast.LENGTH_SHORT).show()
                Log.d("PaymentSDK", "Payment was interrupted. Error code: $errorCode. Message: $message")
            }
        }
    }
}