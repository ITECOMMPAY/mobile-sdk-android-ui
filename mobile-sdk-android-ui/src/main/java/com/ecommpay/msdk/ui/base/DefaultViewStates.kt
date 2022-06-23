package com.ecommpay.msdk.ui.base

sealed class DefaultViewStates<VD : ViewData>(viewData: VD) : ViewStates<VD>(viewData) {
    //состояние загрузки (инициализации)
    data class Loading<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)

    //основное состояние для отображения viewData
    data class Content<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)

    //состояние ошибки
    data class Error<VD : ViewData>(override val viewData: VD) : DefaultViewStates<VD>(viewData)
}