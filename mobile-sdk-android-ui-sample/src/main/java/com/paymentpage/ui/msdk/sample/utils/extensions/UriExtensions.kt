package com.paymentpage.ui.msdk.sample.utils.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

internal fun uriFromResourceId(resourceId: Int, context: Context): Uri {
        return with(context.resources) {
            Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResourcePackageName(resourceId))
                .appendPath(getResourceTypeName(resourceId))
                .appendPath(getResourceEntryName(resourceId))
                .build()
    }
}
