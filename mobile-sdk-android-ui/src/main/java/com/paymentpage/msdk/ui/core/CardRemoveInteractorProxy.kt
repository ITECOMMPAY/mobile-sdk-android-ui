package com.paymentpage.msdk.ui.core

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveDelegate
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveInteractor
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveRequest

internal interface CardRemoveInteractorProxy {
    fun addDelegate(delegate: CardRemoveDelegate)
    fun removeDelegate(delegate: CardRemoveDelegate)
    fun cancel()
    fun sendRequest(request: CardRemoveRequest)
}

internal class CardRemoveInteractorProxyImpl(
    private val interactor: CardRemoveInteractor
) : CardRemoveInteractorProxy, CardRemoveDelegate {
    private val delegates = mutableListOf<CardRemoveDelegate>()

    override fun sendRequest(request: CardRemoveRequest) {
        interactor.execute(request = request, callback = this)
    }

    override fun addDelegate(delegate: CardRemoveDelegate) {
        delegates.add(delegate)
    }

    override fun removeDelegate(delegate: CardRemoveDelegate) {
        delegates.remove(delegate)
    }

    override fun cancel() {
        interactor.cancel()
    }

    override fun onError(code: ErrorCode, message: String) {
        delegates.forEach { it.onError(code = code, message = message) }
    }

    override fun onStartingRemove() {
        delegates.forEach { it.onStartingRemove() }
    }

    override fun onSuccess(result: Boolean) {
        delegates.forEach { it.onSuccess(result = result) }
    }
}

internal class CardRemoveInteractorProxyMockImpl(
    private val onSendRequestCalled: (delegate: CardRemoveDelegate?) -> Unit
) : CardRemoveInteractorProxy {

    private val delegates = mutableListOf<CardRemoveDelegate>()

    override fun addDelegate(delegate: CardRemoveDelegate) {
        delegates.add(delegate)
    }

    override fun removeDelegate(delegate: CardRemoveDelegate) {
        delegates.remove(delegate)
    }

    override fun sendRequest(request: CardRemoveRequest) {
        delegates.forEach { onSendRequestCalled.invoke(it) }
    }

    override fun cancel() {}
}