package com.paymentpage.msdk.ui

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.interactors.analytics.error.ErrorEventDelegate
import com.paymentpage.msdk.core.domain.interactors.analytics.error.ErrorEventInteractor
import com.paymentpage.msdk.core.domain.interactors.analytics.error.ErrorEventRequest
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

    companion object {
        private const val PREFIX_CLASS_NAME = "com.paymentpage"
    }

    private var weakContext: WeakReference<Context>? = null
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null
    private var infos: HashMap<String?, String?> = HashMap()

    fun start(context: Context?) {
        this.weakContext = WeakReference(context)
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this@CrashHandler)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        val context = weakContext?.get()?.applicationContext
        collectDeviceInfo(context)
        val isDone: BlockingQueue<Boolean> = ArrayBlockingQueue(1)
        val newThread = object : Thread() {
            override fun run() {
                errorInteractor.execute(
                    request = ErrorEventRequest(
                        version = infos["versionName"] ?: "",
                        device = Build.DEVICE,
                        model = Build.MODEL,
                        manufacturer = Build.MANUFACTURER,
                        versionCode = infos["versionCode"] ?: "",
                        exceptionName = ex::class.java.simpleName,
                        exceptionDescription = shortStackTrace(ex),
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

    private fun shortStackTrace(ex: Throwable): String {
        val ownStackTrace = ex.stackTrace.filter {
            it.className.startsWith(PREFIX_CLASS_NAME)
        }
        val mainElement = if (ownStackTrace.isNotEmpty()) ownStackTrace[0] else null
        return "${mainElement?.fileName ?: "Unknown file name"}:${mainElement?.lineNumber ?: "Unknown line"}"
    }

    private fun collectDeviceInfo(context: Context?) {
        try {
            //Package Manager
            val pm: PackageManager? = context?.packageManager
            //Get package information
            val pi: PackageInfo? =
                pm?.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            val versionName = if (pi?.versionName == null) "null" else pi.versionName
            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                pi?.longVersionCode.toString()
            } else {
                pi?.versionCode.toString()
            }
            infos["versionName"] = versionName
            infos["versionCode"] = versionCode
        } catch (e: PackageManager.NameNotFoundException) {

        }
    }
}