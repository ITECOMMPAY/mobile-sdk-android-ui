package com.mglwallet.ui.msdk.sample.ui.main.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mglwallet.ui.msdk.sample.BuildConfig
import com.paymentpage.msdk.core.MSDKCoreSession

@Composable
internal fun VersionInfo() {
    Row {
        Text(text = "Ver:")
        Text(
            modifier = Modifier
                .testTag("mSDKUIVersionText"),
            text = BuildConfig.VERSION_NAME,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier
                .testTag("buildNumberText"),
            text = "Build:"
        )
        Text(text = BuildConfig.VERSION_CODE.toString(), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier
                .testTag("mSDKCoreVersionText"),
            text = "Core:"
        )
        Text(text = MSDKCoreSession.metadata.version, fontWeight = FontWeight.Bold)
    }
}