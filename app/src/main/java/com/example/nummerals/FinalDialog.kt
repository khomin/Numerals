package com.example.nummerals

import android.content.DialogInterface
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.final_result_dialog.*

class FinalDialog(val mInflater: LayoutInflater) {

    fun show(
        correct: String?,
        lastValue: String?,
        progress: Int?,
        callbackDismissCallback: () -> Unit
    ) {
        if (progress != null) {
            if (lastValue != null) {
                if (correct != null) {
                    Handler(Looper.getMainLooper()).postDelayed(kotlinx.coroutines.Runnable {
                        val dialogView = mInflater.inflate(R.layout.final_result_dialog, null)
                        val changeIdDialog = AlertDialog.Builder(mInflater.context)
                            .setView(dialogView)
                            .setPositiveButton(mInflater.context.getString(R.string.ok_button)) { _, _ -> }
                            .create()
                        changeIdDialog.show()
                        changeIdDialog.errorValue.text = if(lastValue.isNotEmpty()) { lastValue } else { "-" }
                        changeIdDialog.correctValue.text = correct
                        val resultMessage = "Correct answers, total: $progress"
                        changeIdDialog.finalText.text = resultMessage
                        val acceptDialogButton =
                            changeIdDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        acceptDialogButton.setOnClickListener {
                            changeIdDialog.dismiss()
                            callbackDismissCallback.invoke()
                        }
                    }, 500)
                }
            }
        }
    }
}