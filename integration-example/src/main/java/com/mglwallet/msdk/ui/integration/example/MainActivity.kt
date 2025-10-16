package com.mglwallet.msdk.ui.integration.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

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