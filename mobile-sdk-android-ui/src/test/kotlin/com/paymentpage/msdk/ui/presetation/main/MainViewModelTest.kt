package com.paymentpage.msdk.ui.presetation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.entities.threeDSecure.AcsPage
import com.paymentpage.msdk.ui.core.CardRemoveInteractorProxyMockImpl
import com.paymentpage.msdk.ui.core.PayInteractorProxyMockImpl
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
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should return visible customer fields from state`() = scope.runTest {
        //GIVEN
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
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCustomerFields(customerFields = customerFields)
            },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)
        val expectedCustomerFields = listOf(
            CustomerField(
                name = "first_name",
                label = "First name",
                errorMessageKey = "message_general_invalid"
            )
        )

        //WHEN
        viewModel.saleCard(method = method, needSendCustomerFields = false)
        val actualCustomerFields = viewModel.state.value.customerFields

        //THEN
        assertTrue(actualCustomerFields.isEqual(expectedCustomerFields))
    }

    @Test
    fun `should set state to error state`() = scope.runTest {
        //GIVEN
        val errorCode = mockk<ErrorCode>()
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onError(code = errorCode, "message")
            },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.saleCard(method = method, needSendCustomerFields = false)

        //THEN
        with(viewModel.state.value) {
            assertTrue(error != null)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to successful state`() = scope.runTest {
        //GIVEN
        val payment = mockk<Payment>()
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCompleteWithSuccess(payment = payment)
            },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.saleCard(method = method, needSendCustomerFields = false)

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
        val payment = mockk<Payment>()
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCompleteWithDecline(paymentMessage = null, payment = payment)
            },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.saleCard(method = method, needSendCustomerFields = false)

        //THEN
        with(viewModel.state.value) {
            assertTrue(this.payment != null && this.payment == payment)
            assertTrue(finalPaymentState is FinalPaymentState.Decline)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to try again`() = scope.runTest {
        //GIVEN
        val payment = mockk<Payment>()
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCompleteWithDecline(paymentMessage = null, payment = payment)
            },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.saleCard(method = method, needSendCustomerFields = false)
        viewModel.sendEvent(MainScreenUiEvent.TryAgain)

        //THEN
        with(viewModel.state.value) {
            assertTrue(this.payment != null && this.payment == payment)
            assertTrue(this.currentMethod == null)
            assertTrue(finalPaymentState == null)
            assertTrue(isTryAgain == true)
        }
    }

    @Test
    fun `should set successful state of deleting save card operation`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {},
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {
                it?.onSuccess(true)
            }
        )
        val method = mockk<UIPaymentMethod.UISavedCardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.setCurrentMethod(method = method)
        assertTrue(viewModel.state.value.currentMethod == method)
        viewModel.deleteSavedCard(method = method)

        //THEN
        assertTrue(viewModel.state.value.isDeleteCardLoading == false)
        assertTrue(viewModel.state.value.currentMethod == null)
    }

    @Test
    fun `should set current method to state correctly every time`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl { },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val uiSavedCardPayPaymentMethod =
            mockk<UIPaymentMethod.UISavedCardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.setCurrentMethod(method = uiSavedCardPayPaymentMethod)

        //THEN
        assertTrue(viewModel.state.value.currentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod)

        //GIVEN
        val uiCardPayPaymentMethod =
            mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.setCurrentMethod(method = uiCardPayPaymentMethod)

        //THEN
        assertTrue(viewModel.state.value.currentMethod is UIPaymentMethod.UICardPayPaymentMethod)
    }

    @Test
    fun `should set current payment object to state when status is changing`() = scope.runTest {
        //GIVEN
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl { },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
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

    @Test
    fun `should pass card sale successful flow correctly`() = scope.runTest {
        //GIVEN
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
        val clarificationFields = listOf(
            ClarificationField(
                name = "first_name"
            ),
            ClarificationField(
                name = "last_name",
            )
        )
        val status = mockk<PaymentStatus>()
        val payment = mockk<Payment>()
        val acsPage = mockk<AcsPage>()
        val viewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCustomerFields(customerFields = customerFields)
                it?.onStatusChanged(status = status, payment = payment)
                it?.onClarificationFields(
                    clarificationFields = clarificationFields,
                    payment = payment
                )
                it?.onStatusChanged(status = status, payment = payment)
                it?.onThreeDSecure(acsPage = acsPage, isCascading = false, payment = payment)
                it?.onStatusChanged(status = status, payment = payment)
                it?.onCompleteWithSuccess(payment = payment)
            },
            cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        viewModel.saleCard(method = method, true)

        //THEN
        val finalState = viewModel.state.value
        assertTrue(finalState.payment == payment)
        assertTrue(finalState.isLoading == false)
        assertTrue(finalState.customerFields == emptyList<CustomerField>())
        assertTrue(finalState.clarificationFields == emptyList<ClarificationField>())
        assertTrue(finalState.error == null)
        assertTrue(finalState.acsPageState == null)
        assertTrue(finalState.finalPaymentState is FinalPaymentState.Success)
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
