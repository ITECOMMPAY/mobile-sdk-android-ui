package com.ecommpay.compose_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommpay.compose_sample.ui.theme.AppTheme
import com.ecommpay.compose_sample.utils.SignatureGenerator
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.field.FieldType
import com.ecommpay.msdk.ui.ActionType
import com.ecommpay.msdk.ui.PaymentSDK
import com.ecommpay.msdk.ui.paymentOptions
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SampleMainScreen()
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun SampleMainScreen() {
    val payment = PaymentInfo(
        projectId = 111781,
        paymentId = UUID.randomUUID().toString(),
        paymentAmount = 123,
        paymentCurrency = "RUB",
        customerId = "12",
        paymentDescription = "Test payment"
    )
    payment.signature =
        SignatureGenerator.generateSignature(payment.getParamsForSignature(), "123")

    val paymentOptions = paymentOptions {
        paymentInfo = payment
        actionType = ActionType.Sale
        additionalFields {
            field {
                type = FieldType.CUSTOMER_EMAIL
                value = "mail@mail.ru"
            }
        }
    }
    val sdk = PaymentSDK(context = LocalContext.current, paymentOptions = paymentOptions)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                PaymentSDK.RESULT_SUCCESS -> {}
                PaymentSDK.RESULT_CANCELLED -> {}
                PaymentSDK.RESULT_DECLINE -> {}
                PaymentSDK.RESULT_FAILED -> {}
                PaymentSDK.RESULT_ERROR -> {
                    val errorCode = result.data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
                    val message = result.data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
                }
            }

        }


    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                        onClick = {
                            launcher.launch(sdk.intent)
                        }) {
                        Text(text = "Sale", color = Color.White, fontSize = 22.sp)
                    }
                }

            }
        }
    )
}