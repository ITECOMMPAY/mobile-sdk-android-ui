package com.ecommpay.compose_sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.compose_sample.ui.theme.AppTheme
import com.ecommpay.compose_sample.utils.SignatureGenerator
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.ui.main.PaymentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    SampleMainScreen()
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun SampleMainScreen() {
    val paymentInfo = PaymentInfo(
        projectId = 111781,
        //paymentId = "msdk_core_payment_id_" + getRandomNumber(),
        paymentId = "sdk_android_1650617310",
        paymentAmount = 1000,
        paymentCurrency = "RUB",
        customerId = "12"
    )
    paymentInfo.signature =
        SignatureGenerator.generateSignature(paymentInfo.getParamsForSignature(), "123")
    val paymentIntent = PaymentActivity.buildPaymentIntent(paymentInfo)
    val context = LocalContext.current
    Scaffold(content = {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            item {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        context.startActivity(Intent(paymentIntent))
                    }) {
                        Text(text = "Sale")
                    }
                }
            }
        }
    }
    )
}