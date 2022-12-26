package com.ecommpay.msdk.ui.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun xmlIntegration(view: View) {
        startActivity(Intent(this, XmlActivity::class.java))
    }

    fun composeIntegration(view: View) {
        startActivity(Intent(this, ComposeActivity::class.java))
    }
}