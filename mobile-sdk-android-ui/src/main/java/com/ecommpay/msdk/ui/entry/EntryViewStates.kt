package com.ecommpay.msdk.ui.entry

import com.ecommpay.msdk.ui.base.ViewStates

sealed class EntryViewStates(viewData: EntryViewData): ViewStates<EntryViewData>(viewData) {
    class ShownDeleteDialog(override val viewData: EntryViewData): EntryViewStates(viewData)
}
