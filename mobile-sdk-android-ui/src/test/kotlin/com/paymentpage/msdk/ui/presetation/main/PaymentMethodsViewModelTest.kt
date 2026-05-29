package com.paymentpage.msdk.ui.presetation.main

import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.core.CardRemoveInteractorProxyMockImpl
import com.paymentpage.msdk.ui.presentation.main.PaymentMethodsViewModel
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Test

internal class PaymentMethodsViewModelTest {

    @Test
    fun `should apply sale with token filtering after onSuccess`() {
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {
            it?.onSuccess(result = true)
        }
        val viewModel = PaymentMethodsViewModel(cardRemoveInteractor = cardRemoveInteractor)
        viewModel.configureFilters(
            actionType = SDKActionType.Sale,
            isSaleWithToken = true,
        )

        viewModel.setPaymentMethods(
            listOf(
                createGoogleMethod(index = 0),
                createSavedMethod(index = 1, accountId = 11L),
                createSavedMethod(index = 2, accountId = 12L),
                createCardMethod(index = 3),
                createApsMethod(index = 4, code = "aps")
            )
        )

        val initialState = viewModel.state.value
        assertTrue(initialState.visiblePaymentMethods.size == 5)
        assertTrue(initialState.currentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod)

        val selectedSavedMethod = initialState.currentMethod as UIPaymentMethod.UISavedCardPayPaymentMethod
        viewModel.deleteSavedCard(selectedSavedMethod)

        val stateAfterDelete = viewModel.state.value
        assertTrue(stateAfterDelete.visiblePaymentMethods.size == 1)
        assertTrue(stateAfterDelete.visiblePaymentMethods.all { it.method is UIPaymentMethod.UISavedCardPayPaymentMethod })
        assertTrue(stateAfterDelete.visiblePaymentMethods.count { it.isSelected } == 1)
        assertTrue(stateAfterDelete.currentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod)
    }

    @Test
    fun `should toggle selected method in view model`() {
        val viewModel = PaymentMethodsViewModel(cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {})
        viewModel.configureFilters(
            actionType = SDKActionType.Sale,
            isSaleWithToken = false,
        )
        viewModel.setPaymentMethods(
            listOf(
                createCardMethod(index = 0),
                createApsMethod(index = 1, code = "aps")
            )
        )

        val selectedMethod = viewModel.state.value.currentMethod ?: error("No default selected method")

        viewModel.onPaymentMethodClick(selectedMethod)
        assertTrue(viewModel.state.value.currentMethod == null)
        assertTrue(viewModel.state.value.visiblePaymentMethods.none { it.isSelected })

        viewModel.onPaymentMethodClick(selectedMethod)
        assertTrue(viewModel.state.value.currentMethod != null)
        assertTrue(viewModel.state.value.visiblePaymentMethods.count { it.isSelected } == 1)
    }

    @Test
    fun `should keep selected method after payment methods refresh`() {
        val viewModel = PaymentMethodsViewModel(cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {})
        viewModel.configureFilters(
            actionType = SDKActionType.Sale,
            isSaleWithToken = false,
        )

        viewModel.setPaymentMethods(
            listOf(
                createCardMethod(index = 0),
                createApsMethod(index = 1, code = "aps")
            )
        )

        val apsMethod = viewModel.state.value.visiblePaymentMethods
            .map { it.method }
            .filterIsInstance<UIPaymentMethod.UIApsPaymentMethod>()
            .first()
        viewModel.onPaymentMethodClick(apsMethod)

        viewModel.setPaymentMethods(
            listOf(
                createCardMethod(index = 0),
                createApsMethod(index = 1, code = "aps")
            )
        )

        assertTrue(viewModel.state.value.currentMethod is UIPaymentMethod.UIApsPaymentMethod)
        assertTrue(viewModel.state.value.visiblePaymentMethods.count { it.isSelected } == 1)
    }

    @Test
    fun `should remove selected saved card after delete`() {
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {
            it?.onSuccess(result = true)
        }
        val viewModel = PaymentMethodsViewModel(cardRemoveInteractor = cardRemoveInteractor)
        viewModel.configureFilters(
            actionType = SDKActionType.Sale,
            isSaleWithToken = false,
        )

        viewModel.setPaymentMethods(
            listOf(
                createSavedMethod(index = 0, accountId = 100L),
                createCardMethod(index = 1)
            )
        )

        val savedMethod = viewModel.state.value.visiblePaymentMethods
            .map { it.method }
            .filterIsInstance<UIPaymentMethod.UISavedCardPayPaymentMethod>()
            .first()

        viewModel.onPaymentMethodClick(savedMethod)
        viewModel.deleteSavedCard(savedMethod)

        val state = viewModel.state.value
        assertTrue(state.visiblePaymentMethods.none { item ->
            item.method is UIPaymentMethod.UISavedCardPayPaymentMethod &&
                    item.method.accountId == savedMethod.accountId
        })
        assertTrue(state.currentMethod !is UIPaymentMethod.UISavedCardPayPaymentMethod)
    }

    private fun PaymentMethodsViewModel.configureFilters(
        actionType: SDKActionType,
        isSaleWithToken: Boolean,
    ) {
        setPrivateField("actionType", actionType)
        setPrivateField("isSaleWithToken", isSaleWithToken)
    }

    private fun PaymentMethodsViewModel.setPrivateField(name: String, value: Any) {
        val field = PaymentMethodsViewModel::class.java.getDeclaredField(name)
        field.isAccessible = true
        field.set(this, value)
    }

    private fun createGoogleMethod(index: Int): UIPaymentMethod.UIGooglePayPaymentMethod {
        return UIPaymentMethod.UIGooglePayPaymentMethod(
            index = index,
            title = "Google Pay",
            logoUrl = null,
            paymentMethod = mockPaymentMethod(code = "google_pay")
        )
    }

    private fun createSavedMethod(index: Int, accountId: Long): UIPaymentMethod.UISavedCardPayPaymentMethod {
        return UIPaymentMethod.UISavedCardPayPaymentMethod(
            index = index,
            title = "**** $accountId",
            savedAccount = mockSavedAccount(accountId),
            paymentMethod = mockPaymentMethod(code = "card")
        )
    }

    private fun createCardMethod(index: Int): UIPaymentMethod.UICardPayPaymentMethod {
        return UIPaymentMethod.UICardPayPaymentMethod(
            index = index,
            title = "Card",
            logoUrl = null,
            paymentMethod = mockPaymentMethod(code = "card")
        )
    }

    private fun createApsMethod(index: Int, code: String): UIPaymentMethod.UIApsPaymentMethod {
        return UIPaymentMethod.UIApsPaymentMethod(
            index = index,
            title = code,
            paymentMethod = mockPaymentMethod(code = code)
        )
    }

    private fun mockPaymentMethod(code: String): PaymentMethod {
        return mockk(relaxed = true) {
            every { this@mockk.code } returns code
            every { this@mockk.iconUrl } returns null
            every { this@mockk.customerFields } returns emptyList()
            every { this@mockk.availableCardTypes } returns emptyList()
            every { this@mockk.translations } returns emptyMap()
        }
    }

    private fun mockSavedAccount(id: Long): SavedAccount {
        return mockk(relaxed = true) {
            every { this@mockk.id } returns id
            every { this@mockk.number } returns "**** $id"
            every { this@mockk.cardType } returns "visa"
            every { this@mockk.cardExpiry } returns null
        }
    }
}
