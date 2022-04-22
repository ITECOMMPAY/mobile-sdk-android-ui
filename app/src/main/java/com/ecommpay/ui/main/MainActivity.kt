package com.ecommpay.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.ui.main.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.ui.theme.SDKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SDKTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    PaymentMethodsScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SDKTheme {
        PaymentMethodsList(listOf(
            ItemPaymentMethodViewData(
                icon = android.R.drawable.btn_star_big_on,
                name = "card"),
            ItemPaymentMethodViewData(
                icon = android.R.drawable.ic_delete,
                name = "card"))
        ) {}
    }
}