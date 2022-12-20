package com.paymentpage.msdk.ui.core

import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveDelegate
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveInteractor
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveRequest

internal interface CardRemoveInteractorProxy {
    var delegate: CardRemoveDelegate?

    fun cancel()
    fun sendRequest(request: CardRemoveRequest)
}

internal class CardRemoveInteractorProxyImpl(
    private val interactor: CardRemoveInteractor
) : CardRemoveInteractorProxy {
    override var delegate: CardRemoveDelegate? = null

    override fun sendRequest(request: CardRemoveRequest) {
        interactor.execute(request = request, callback = delegate)
    }

    override fun cancel() {
        interactor.cancel()
    }
}

internal class CardRemoveInteractorProxyMockImpl(
    private val onSendRequestCalled: (delegate: CardRemoveDelegate?) -> Unit
) : CardRemoveInteractorProxy {
    override var delegate: CardRemoveDelegate? = null

    override fun sendRequest(request: CardRemoveRequest) {
        onSendRequestCalled.invoke(delegate)
    }

    override fun cancel() {}
}