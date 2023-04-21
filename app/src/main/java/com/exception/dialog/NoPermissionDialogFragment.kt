package com.exception.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NoPermissionDialogFragment(): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
            .setTitle("Нам жаль, но...")
            .setMessage("Приложение не работает без разрешений на сбор геолокации!")
            .setPositiveButton("Понятно", DialogInterface.OnClickListener { dialogInterface, buttonCode ->
                activity?.finishAffinity()
            })
            .setCancelable(false)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
        (dialog as AlertDialog).getButton(Dialog.BUTTON_NEGATIVE).isEnabled = false
    }
}