package com.ecommpay.ui.msdk.sample.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

internal fun bitmapFromUri(uri: Uri, context: Context): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(context.contentResolver, uri)
    } else {
        val source = ImageDecoder
            .createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}
