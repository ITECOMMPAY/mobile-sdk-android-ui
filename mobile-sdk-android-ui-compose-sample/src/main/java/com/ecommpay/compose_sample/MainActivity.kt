package com.ecommpay.compose_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.ecommpay.msdk.ui.PaymentSDK
import com.ecommpay.msdk.ui.paymentOptions

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
    val paymentOptions = paymentOptions {
        projectId = 111781
        //paymentId = "msdk_core_payment_id_" + getRandomNumber(),
        //paymentId = "sdk_android_163243123123"
        paymentAmount = 123
        paymentCurrency = "RUB"
        customerId = "12"
    }
    paymentOptions.signature =
        SignatureGenerator.generateSignature(paymentOptions.getParamsForSignature(), "123")

    val sdk = PaymentSDK(context = LocalContext.current, paymentOptions = paymentOptions)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        }


    Scaffold(
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            launcher.launch(sdk.intent)
                        }) {
                            Text(text = "Sale")
                        }
                    }
                }
            }
        }
    )
}