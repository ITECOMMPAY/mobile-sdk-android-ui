package com.ecommpay.msdk.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.base.MessageAlert
import com.ecommpay.msdk.ui.base.MessageToast
import com.ecommpay.msdk.ui.navigation.NavigationScreen
import com.ecommpay.msdk.ui.theme.SDKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            SDKTheme {
                NavigationScreen(viewModel.isFirstRun) { action ->
                    when (action) {
                        is DefaultViewActions.ShowMessage -> {
                            when (val message =
                                action.message) {
                                is MessageAlert -> {
                                    Toast.makeText(
                                        this,
                                        message.message,
                                        Toast.LENGTH_LONG).show()
                                }
                                is MessageToast -> {
                                    Toast.makeText(
                                        this,
                                        message.message,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}