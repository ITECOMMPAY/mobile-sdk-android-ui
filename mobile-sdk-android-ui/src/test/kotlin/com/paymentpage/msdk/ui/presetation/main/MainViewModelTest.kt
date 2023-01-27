package com.paymentpage.msdk.ui.presetation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePage
import com.paymentpage.msdk.ui.SDKActionType
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
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
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
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCustomerFields(customerFields = customerFields)
            },
            cardRemoveInteractor = cardRemoveInteractor
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
        mainViewModel.payNewCard(
            actionType = SDKActionType.Sale,
            method = method,
            needSendCustomerFields = false
        )
        val actualCustomerFields = mainViewModel.state.value.customerFields

        //THEN
        assertTrue(actualCustomerFields.isEqual(expectedCustomerFields))
    }

    @Test
    fun `should set state to error state`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        val errorCode = mockk<ErrorCode>()
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onError(code = errorCode, "message")
            },
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        mainViewModel.payNewCard(
            actionType = SDKActionType.Sale,
            method = method,
            needSendCustomerFields = false
        )

        //THEN
        with(mainViewModel.state.value) {
            assertTrue(error != null)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to successful state`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        val payment = mockk<Payment>()
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCompleteWithSuccess(payment = payment)
            },
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        mainViewModel.payNewCard(
            actionType = SDKActionType.Sale,
            method = method,
            needSendCustomerFields = false
        )

        //THEN
        with(mainViewModel.state.value) {
            assertTrue(finalPaymentState is FinalPaymentState.Success)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to decline state`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        val payment = mockk<Payment>()
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCompleteWithDecline(paymentMessage = null, payment = payment)
            },
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        mainViewModel.payNewCard(
            actionType = SDKActionType.Sale,
            method = method,
            needSendCustomerFields = false
        )

        //THEN
        with(mainViewModel.state.value) {
            assertTrue(finalPaymentState is FinalPaymentState.Decline)
            assertTrue(isLoading == false)
        }
    }

    @Test
    fun `should set state to try again`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        val payment = mockk<Payment>()
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCompleteWithDecline(paymentMessage = null, payment = payment)
            },
            cardRemoveInteractor = cardRemoveInteractor
        )
        val paymentMethodsViewModel = PaymentMethodsViewModel(
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        mainViewModel.payNewCard(
            actionType = SDKActionType.Sale,
            method = method,
            needSendCustomerFields = false
        )
        mainViewModel.sendEvent(MainScreenUiEvent.TryAgain)

        //THEN
        with(mainViewModel.state.value) {
            assertTrue(finalPaymentState == null)
            assertTrue(isTryAgain == true)
        }

        assertTrue(paymentMethodsViewModel.state.value.currentMethod == null)
    }

    @Test
    fun `should set successful state of deleting save card operation`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {
            it?.onSuccess(true)
        }
        val paymentMethodsViewModel = PaymentMethodsViewModel(
            cardRemoveInteractor = cardRemoveInteractor
        )
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {},
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UISavedCardPayPaymentMethod>(relaxed = true)

        //WHEN
        paymentMethodsViewModel.setCurrentMethod(method = method)
        assertTrue(paymentMethodsViewModel.state.value.currentMethod == method)
        paymentMethodsViewModel.deleteSavedCard(method = method)
        paymentMethodsViewModel.setCurrentMethod(method = null)
        //THEN
        assertTrue(mainViewModel.state.value.isDeleteCardLoading == false)
        assertTrue(paymentMethodsViewModel.state.value.currentMethod == null)
    }

    @Test
    fun `should set current method to state correctly every time`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
        val uiSavedCardPayPaymentMethod =
            mockk<UIPaymentMethod.UISavedCardPayPaymentMethod>(relaxed = true)
        val paymentMethodsViewModel = PaymentMethodsViewModel(
            cardRemoveInteractor = cardRemoveInteractor
        )
        //WHEN
        paymentMethodsViewModel.setCurrentMethod(method = uiSavedCardPayPaymentMethod)

        //THEN
        assertTrue(paymentMethodsViewModel.state.value.currentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod)

        //GIVEN
        val uiCardPayPaymentMethod =
            mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        paymentMethodsViewModel.setCurrentMethod(method = uiCardPayPaymentMethod)

        //THEN
        assertTrue(paymentMethodsViewModel.state.value.currentMethod is UIPaymentMethod.UICardPayPaymentMethod)
    }

    @Test
    fun `should pass card sale successful flow correctly`() = scope.runTest {
        //GIVEN
        val cardRemoveInteractor = CardRemoveInteractorProxyMockImpl {}
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
        val threeDSecurePage = mockk<ThreeDSecurePage>()
        val mainViewModel = MainViewModel(
            payInteractor = PayInteractorProxyMockImpl {
                it?.onCustomerFields(customerFields = customerFields)
                it?.onStatusChanged(status = status, payment = payment)
                it?.onClarificationFields(
                    clarificationFields = clarificationFields,
                    payment = payment
                )
                it?.onStatusChanged(status = status, payment = payment)
                it?.onThreeDSecure(
                    threeDSecurePage = threeDSecurePage,
                    isCascading = false,
                    payment = payment
                )
                it?.onStatusChanged(status = status, payment = payment)
                it?.onCompleteWithSuccess(payment = payment)
            },
            cardRemoveInteractor = cardRemoveInteractor
        )
        val method = mockk<UIPaymentMethod.UICardPayPaymentMethod>(relaxed = true)

        //WHEN
        mainViewModel.payNewCard(
            actionType = SDKActionType.Sale,
            method = method,
            needSendCustomerFields = true
        )

        //THEN
        val finalState = mainViewModel.state.value
        assertTrue(finalState.isLoading == false)
        assertTrue(finalState.customerFields == emptyList<CustomerField>())
        assertTrue(finalState.clarificationFields == emptyList<ClarificationField>())
        assertTrue(finalState.error == null)
        assertTrue(finalState.threeDSecurePageState == null)
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
