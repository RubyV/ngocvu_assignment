package com.ngocvu.example.utils

import androidx.fragment.app.FragmentActivity
import com.ngocvu.example.view.dialog.SendEmailDialog

class DialogFragmentUtil {

    companion object {
        fun showSendEmailDialog(activity: FragmentActivity){
            val dialogFragment = SendEmailDialog.newInstance()
            dialogFragment.show(activity.supportFragmentManager, "asuioyfhauishfuiashf")
        }

    }
}