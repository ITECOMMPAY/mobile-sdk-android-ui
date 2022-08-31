package com.paymentpage.msdk.ui

import android.content.*
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.paymentpage.msdk.core.base.ErrorCode
import java.lang.ref.WeakReference


object CrashHandler : Thread.UncaughtExceptionHandler {
    private var context: WeakReference<Context>? = null
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null
    private var infos: HashMap<String, String> = HashMap()

    fun init(context: Context) {
        this.context = WeakReference(context)
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        collectDeviceInfo(context?.get())
        object : Thread() {
            override fun run() {
                Looper.prepare()
                if (context?.get() != null) {
                    val message = "Stack Trace:\n${ex.stackTraceToString()}\nPayment SDK Information:\n${infos.map { "${it.key}: ${it.value}" }.joinToString(separator = ";\n")}"
                    val builder = AlertDialog.Builder(context?.get()!!, R.style.CrashDialogTheme)
                    builder
                        .setMessage("Error code: ${ErrorCode.UNKNOWN}\nMessage: $message")
                        .setPositiveButton(R.string.ok_label) { _: DialogInterface?, _: Int ->
                            val clipboardManager = context?.get()?.getSystemService(ComponentActivity.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipData = ClipData.newPlainText("StackTrace", message)
                            clipboardManager.setPrimaryClip(clipData)
                            Process.killProcess(Process.myPid())
                        }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
                Looper.loop()
            }
        }.start()
    }

    private fun collectDeviceInfo(context: Context?) {
        try {
            //Package Manager
            val pm: PackageManager? = context?.packageManager
            //Get package information
            val pi: PackageInfo? = pm?.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
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