package com.example.very_new_travel.adapter

import android.app.AlertDialog
import android.content.Context
import com.example.very_new_travel.R
import kotlinx.android.synthetic.main.activity_main.*

class AlertDialogManager {
    fun showAlertDialog(
        context: Context?,
        title: String?,
        message: String?,
        status: Boolean?
    ) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        if (status != null) alertDialog.setIcon(if (status) R.drawable.ic_success else R.drawable.ic_fail)
        alertDialog.setButton("OK") { dialog, which -> }
        alertDialog.show()
    }
}