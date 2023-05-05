package com.paymentpage.msdk.ui

import android.content.Context
import android.os.Build
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.interactors.analytics.error.ErrorEventDelegate
import com.paymentpage.msdk.core.domain.interactors.analytics.error.ErrorEventInteractor
import com.paymentpage.msdk.core.domain.interactors.analytics.error.ErrorEventRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import java.lang.ref.WeakReference
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

internal class CrashHandler(
    private val projectId: Long? = null,
    private val paymentId: String? = null,
    private val customerId: String? = null,
    private val signature: String? = null,
    private val errorInteractor: ErrorEventInteractor
) : Thread.UncaughtExceptionHandler {
    private var weakContext: WeakReference<Context>? = null
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null

    fun start(context: Context?) {
        this.weakContext = WeakReference(context)
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this@CrashHandler)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun uncaughtException(thread: Thread, ex: Throwable) {

        val isDone: BlockingQueue<Boolean> = ArrayBlockingQueue(1)
        val stackTrace = getStackTrace(ex)
        val newThread = object : Thread() {
            override fun run() {

                errorInteractor.execute(
                    request = ErrorEventRequest(
                        version = BuildConfig.SDK_VERSION_NAME,
                        device = Build.DEVICE,
                        model = Build.MODEL,
                        manufacturer = Build.MANUFACTURER,
                        exceptionName = ex::class.java.simpleName,
                        exceptionDescription = stackTrace,
                        projectId = projectId,
                        paymentId = paymentId,
                        customerId = customerId,
                        signature = signature
                    ), callback = object : ErrorEventDelegate {

                        override fun onError(code: ErrorCode, message: String) {
                            isDone.put(true)
                        }

                        override fun onSuccess() {
                            isDone.put(true)
                        }

                    }
                )
            }
        }
        newThread.start()
        isDone.take()
        defaultHandler?.uncaughtException(thread, ex)
    }

    private fun getStackTrace(ex: Throwable): String {
        val ownStackTrace = ex.stackTrace.filter {
            it.className.startsWith(BuildConfig.LIBRARY_PACKAGE_NAME)
        }
        val mainElement = if (ownStackTrace.isNotEmpty()) ownStackTrace[0] else null
        return "${mainElement?.fileName ?: "Unknown file name"}:${mainElement?.lineNumber ?: "Unknown line"}"
    }
}
