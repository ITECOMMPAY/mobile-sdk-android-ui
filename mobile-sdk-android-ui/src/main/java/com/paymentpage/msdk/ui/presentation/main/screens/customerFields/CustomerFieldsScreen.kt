package com.paymentpage.msdk.ui.presentation.main.screens.customerFields

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.presentation.main.fillCustomerFields
import com.paymentpage.msdk.ui.presentation.main.sendCustomerFields
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.ExpandablePaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun CustomerFieldsScreen(
    actionType: SDKActionType,
    onCancel: () -> Unit,
    onBack: () -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val state = mainViewModel.lastState
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val method = paymentMethodsViewModel.lastState.currentMethod
    val customerFields = mainViewModel.lastState.customerFields
    var isCustomerFieldsValid by remember { mutableStateOf(method?.isCustomerFieldsValid ?: false) }

    BackHandler(true) { onBack() }

    SDKScaffold(
        title = getStringOverride(OverridesKeys.TITLE_PAYMENT_ADDITIONAL_DATA),
        scrollableContent = {
            if (actionType != SDKActionType.Tokenize) {
                ExpandablePaymentOverview(
                    actionType = actionType,
                    expandable = true
                )
                Spacer(modifier = Modifier.size(24.dp))
            }

            Box(
                modifier = Modifier
                    .background(
                        color = SDKTheme.colors.container,
                        shape = SDKTheme.shapes.radius20
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    if (actionType != SDKActionType.Tokenize) {
                        Spacer(modifier = Modifier.size(20.dp))

                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.ADDITIONAL_DATA_DISCLAIMER_TEXT),
                            text = getStringOverride(OverridesKeys.TITLE_PAYMENT_ADDITIONAL_DATA_DISCLAIMER),
                            style = SDKTheme.typography.s14Normal
                        )
                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    CustomerFields(
                        customerFieldValues = method?.customerFieldValues ?: emptyList(),
                        customerFields = customerFields,
                        additionalFields = LocalPaymentOptions.current.additionalFields,
                        onCustomerFieldsChanged = { fields, isValid ->
                            isCustomerFieldsValid = isValid
                            method?.customerFieldValues = fields
                            method?.isCustomerFieldsValid = isValid
                        }
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    CustomerFieldsButton(
                        actionType = actionType,
                        isEnabled = isCustomerFieldsValid
                    ) {
                        val customerFieldValues = method?.customerFieldValues ?: emptyList()
                        if (state.request == null)
                            mainViewModel.sendCustomerFields(customerFieldValues)
                        else {
                            mainViewModel.fillCustomerFields(
                                customerFields = customerFieldValues,
                                request = state.request
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.size(24.dp))
            SDKFooter()
            Spacer(modifier = Modifier.size(24.dp))
        },
        onClose = { onCancel() },
        onBack = { onBack() }
    )
}
