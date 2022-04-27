package com.ecommpay.msdk.ui.base

sealed class DefaultViewStates<VD : ViewData>(viewData: VD) : ViewStates<VD>(viewData) {
    //состояние при инициализации
    data class Default<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)

    //возвратное состояние - showLoading() else hideLoading()
    data class Loading<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)

    //возвратное состояние - blockUI() else unblockUI(), вызывается когда нужно заблокировать ui,
    //например когда выполняется какой-нибудь ViewAction
    data class BlockUI<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)

    //основное состояние для отображения viewData
    data class Display<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)

    //возвратное состояние - disabledNetwork() else enabledNetwork(), вызывается при отсутствие интернета
    data class DisabledNetwork<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)
}