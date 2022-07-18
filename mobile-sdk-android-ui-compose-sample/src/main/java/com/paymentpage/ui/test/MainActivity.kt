package com.paymentpage.ui.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecommpay.msdk.ui.PaymentSDK
import com.ecommpay.msdk.ui.paymentOptions
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.ui.test.ui.MainScreen
import com.paymentpage.ui.test.utils.SignatureGenerator
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold() {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .verticalScroll(rememberScrollState())
                                .padding(it),
                        ) {
                            App(activity = this@MainActivity)
                        }
                    }

                }
            }
        }
    }

    companion object {

        var projectId = 111781
        var paymentId = UUID.randomUUID().toString()
        var paymentAmount: Long = 123
        var paymentCurrency = "USD"
        var customerId = "12"
        var paymentDescription = "Test payment"


        var secretKey: String = "123"
        var apiHost: String = "pp-sdk.westresscode.net"
        var wsApiHost: String = "paymentpage.westresscode.net"
    }

    fun startPaymentPage() {
        val payment = PaymentInfo(
            projectId = projectId,
            paymentId = paymentId,
            paymentAmount = paymentAmount,
            paymentCurrency = paymentCurrency,
            customerId = customerId,
            paymentDescription = paymentDescription
        )
        payment.signature =
            SignatureGenerator.generateSignature(
                payment.getParamsForSignature(), secretKey
            )
        val paymentOptions = paymentOptions {
            paymentInfo = payment
        }
        val sdk = PaymentSDK(context = this.applicationContext, paymentOptions = paymentOptions)

        val intent = sdk.intent
        intent.putExtra(Constants.EXTRA_API_HOST, apiHost)
        intent.putExtra(
            Constants.EXTRA_WS_API_HOST,
            wsApiHost
        )

        startActivityForResult(intent, 2405)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            PaymentSDK.RESULT_SUCCESS -> {}
            PaymentSDK.RESULT_CANCELLED -> {}
            PaymentSDK.RESULT_DECLINE -> {}
            PaymentSDK.RESULT_FAILED -> {}
            PaymentSDK.RESULT_ERROR -> {
                val errorCode = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
                val message = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
            }
        }
    }
}


@Composable
fun App(activity: MainActivity) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") {
            MainScreen(navController = navController, activity = activity)
        }
    }
}


//@Composable
//@Preview(showSystemUi = true)
//fun SampleMainScreen() {
//    val payment = PaymentInfo(
//        projectId = 111781,
//        paymentId = UUID.randomUUID().toString(),
//        paymentAmount = 123,
//        paymentCurrency = "RUB",
//        customerId = "12",
//        paymentDescription = "Test payment"
//    )
//    payment.signature =
//        SignatureGenerator.generateSignature(payment.getParamsForSignature(), "123")
//
//    val context = LocalContext.current
//
//    val paymentOptions = paymentOptions {
//        paymentInfo = payment
//        actionType = ActionType.Sale
//        logoImage =
//            BitmapFactory.decodeResource(context.resources, R.drawable.test_logo)
//        additionalFields {
//            field {
//                type = FieldType.CUSTOMER_EMAIL
//                value = "mail@mail.ru"
//            }
//        }
//    }
//    val sdk = PaymentSDK(context = LocalContext.current, paymentOptions = paymentOptions)
//
//    val launcher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            when (result.resultCode) {
//                PaymentSDK.RESULT_SUCCESS -> {}
//                PaymentSDK.RESULT_CANCELLED -> {}
//                PaymentSDK.RESULT_DECLINE -> {}
//                PaymentSDK.RESULT_FAILED -> {}
//                PaymentSDK.RESULT_ERROR -> {
//                    val errorCode = result.data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
//                    val message = result.data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
//                }
//            }
//
//        }
//
//
//    Scaffold(
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(it),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Box(
//                    modifier = Modifier.padding(15.dp)
//                ) {
//                    Button(modifier = Modifier
//                        .fillMaxWidth()
//                        .height(60.dp),
//                        onClick = {
//                            val intent = sdk.intent
//                            intent.putExtra(Constants.EXTRA_API_HOST, "pp-sdk.westresscode.net")
//                            intent.putExtra(
//                                Constants.EXTRA_WS_API_HOST,
//                                "paymentpage.westresscode.net"
//                            )
//                            launcher.launch(intent)
//                        }) {
//                        Text(text = "Sale", color = Color.White, fontSize = 22.sp)
//                    }
//                }
//
//            }
//        }
//    )
//}