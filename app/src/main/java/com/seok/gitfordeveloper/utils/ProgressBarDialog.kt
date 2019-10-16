package com.seok.gitfordeveloper.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.seok.gitfordeveloper.R

class ProgressbarDialog(context: Context){
    private val dialog = Dialog(context)

    init{
        dialog.setContentView(R.layout.progressbar_dialog)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
    }
    fun show(){
        dialog.show()
    }
    fun hide(){
        dialog.hide()
        dialog.dismiss()
    }

}
