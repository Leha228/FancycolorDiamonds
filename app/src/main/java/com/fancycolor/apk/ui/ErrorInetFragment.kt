package com.fancycolor.apk.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.DialogFragment
import kotlin.system.exitProcess


class ErrorInetFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Отсутствует интернет")
                .setMessage("Попробуйте включить передачу данных и обновите приложение")
                .setPositiveButton(Html.fromHtml("<font color='#111111'>Закрыть</font>")) { dialog, id ->
                    dialog.cancel()
                    exitProcess(-1)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}