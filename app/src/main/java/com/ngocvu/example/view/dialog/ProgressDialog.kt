package com.ngocvu.example.view.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Window
import com.ngocvu.example.R

class ProgressDialog {
    private var dialog: Dialog? = null
    fun show(activity: Activity?) {
        if (dialog != null && dialog!!.isShowing) {
            return
        }
        if (activity!!.isFinishing) return
        dialog = Dialog(activity, R.style.ProgressBarStyle)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_loading)
        dialog!!.setCancelable(false)
        if (activity != null && !activity.isFinishing) {
            dialog!!.setOwnerActivity(activity)
            dialog!!.show()
        }
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            val activity = dialog!!.ownerActivity
            if (activity != null && !activity.isFinishing) {
                dialog!!.dismiss()
            }
        }
    }

    companion object {
        private var mInstance: ProgressDialog? = null
        @get:Synchronized
        val instance: ProgressDialog?
            get() {
                if (mInstance == null) {
                    mInstance =
                        ProgressDialog()
                }
                return mInstance
            }
    }
}