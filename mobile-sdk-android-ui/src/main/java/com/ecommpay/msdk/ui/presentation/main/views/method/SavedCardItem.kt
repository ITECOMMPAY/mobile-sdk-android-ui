package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
internal fun SavedCardItem(
    position: Int,
    isExpand: Boolean,
    savedAccount: SavedAccount,
    onExpand: (position: Int) -> Unit,
) {
    ExpandablePaymentMethodItem(
        position = position,
        name = savedAccount.number ?: "***",
        iconUrl = savedAccount.cardUrlLogo,
        onExpand = onExpand,
        isExpanded = isExpand
    ) {
        Spacer(
            modifier = Modifier // testing content
                .fillMaxWidth()
                .height(100.dp)
        )
    }
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
}
