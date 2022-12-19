package com.paymentpage.msdk.ui.presetation.main

import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.MSDKCoreSessionConfig
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveInteractor
import com.paymentpage.msdk.core.domain.interactors.pay.PayInteractor
import com.paymentpage.msdk.ui.presentation.main.*
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import io.mockk.*
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {
    private lateinit var msdkCoreSession: MSDKCoreSession
    private lateinit var msdkCoreSessionConfig: MSDKCoreSessionConfig
    private lateinit var payInteractor: PayInteractor
    private lateinit var cardRemoveInteractor: CardRemoveInteractor

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        msdkCoreSessionConfig = MSDKCoreSessionConfig.mockFullSuccessFlow()
        msdkCoreSession = MSDKCoreSession(msdkCoreSessionConfig)
        payInteractor = msdkCoreSession.getPayInteractor()
        cardRemoveInteractor = msdkCoreSession.getCardRemoveInteractor()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        msdkCoreSession.cancel()
    }

    @Test
    fun `should return visible customer fields from state`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val customerFields = listOf(
            CustomerField(
                name = "first_name",
                label = "First name",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                isHidden = true,
                label = "Last name",
                errorMessageKey = "message_general_invalid"
            )
        )
        val expectedCustomerFields = listOf(
            CustomerField(
                name = "first_name",
                label = "First name",
                errorMessageKey = "message_general_invalid"
            )
        )
        //WHEN
        viewModel.onCustomerFields(customerFields = customerFields)
        val actualCustomerFields = viewModel.state.value.customerFields
        //THEN
        assertTrue(actualCustomerFields.isEqual(expectedCustomerFields))
    }

    @Test
    fun `should set state to error state`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val errorCode = mockk<ErrorCode>()
        //WHEN
        viewModel.onError(errorCode, "message")
        //THEN
        with(viewModel.state.value) {
            assertTrue(error != null)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to successful state`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val payment = mockk<Payment>()
        //WHEN
        viewModel.onCompleteWithSuccess(payment = payment)
        //THEN
        with(viewModel.state.value) {
            assertTrue(this.payment != null && this.payment == payment)
            assertTrue(finalPaymentState is FinalPaymentState.Success)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to decline state`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val payment = mockk<Payment>()
        //WHEN
        viewModel.onCompleteWithDecline(payment = payment, paymentMessage = "message")
        //THEN
        with(viewModel.state.value) {
            assertTrue(this.payment != null && this.payment == payment)
            assertTrue(finalPaymentState is FinalPaymentState.Decline)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to loading during deleting save card`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UISavedCardPayPaymentMethod>(relaxed = true)
        //WHEN
        viewModel.setCurrentMethod(method = method)
        viewModel.deleteSavedCard(method = method)
        //THEN
        assertTrue(viewModel.state.value.isDeleteCardLoading == true)
        assertTrue(viewModel.state.value.currentMethod == method)
        //WHEN
        viewModel.onSuccess(true)
        //THEN
        assertTrue(viewModel.state.value.isDeleteCardLoading == false)
        assertTrue(viewModel.state.value.currentMethod == null)
    }

    @Test
    fun `should set current method every time to state correctly`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val uiSavedCardPayPaymentMethod =
            mockk<UIPaymentMethod.UISavedCardPayPaymentMethod>(relaxed = true)
        //WHEN
        viewModel.setCurrentMethod(method = uiSavedCardPayPaymentMethod)
        //THEN
        assertTrue(viewModel.state.value.currentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod)
        //GIVEN
        val uiCardPayPaymentMethod = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)
        //WHEN
        viewModel.setCurrentMethod(method = uiCardPayPaymentMethod)
        //THEN
        assertTrue(viewModel.state.value.currentMethod is UIPaymentMethod.UICardPayPaymentMethod)
    }

    @Test
    fun `should set current payment object to state when status is changing`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = payInteractor,
            cardRemoveInteractor = cardRemoveInteractor
        )
        val status = mockk<PaymentStatus>()
        var payment = mockk<Payment>()
        //WHEN
        viewModel.sendEvent(MainScreenUiEvent.SetPayment(payment = payment))
        //THEN
        assertTrue(viewModel.state.value.payment == payment)
        //GIVEN
        payment = mockk()
        //WHEN
        viewModel.onStatusChanged(status = status, payment = payment)
        //THEN
        assertTrue(viewModel.state.value.payment == payment)
    }
}

private fun CustomerField.isEqual(customerField: CustomerField): Boolean =
    when {
        this == customerField -> true
        this.name == customerField.name &&
                this.errorMessageKey == customerField.errorMessageKey &&
                this.label == customerField.label -> true
        else -> false
    }

private fun List<CustomerField>.isEqual(customerFields: List<CustomerField>): Boolean =
    when {
        this == customerFields -> true
        this.size == customerFields.size -> {
            var result = true
            this.forEachIndexed { index, customerField ->
                if (!customerField.isEqual(customerFields[index])) {
                    result = false
                }
            }
            result
        }
        else -> false
    }
