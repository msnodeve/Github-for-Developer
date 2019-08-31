package com.seok.gitfordeveloper.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.seok.gitfordeveloper.R

class ProgressbarDialog(context: Context){
    private val dialog = Dialog(context)

    init{
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
    }
    public fun start(){
        dialog.setContentView(R.layout.progressbar_dialog)
        dialog.show()
    }
    public fun finish(){
        dialog.dismiss()
    }
}
