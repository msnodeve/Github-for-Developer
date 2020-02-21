package com.seok.gfd.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CommonUtils private constructor(){
    private object INSTANCE{
        val instance = CommonUtils()
        val gson = GsonBuilder().create()
    }

    private var screenWidth = 0f
    private var screenHeight = 0f

    companion object{
        val instance : CommonUtils by lazy { INSTANCE.instance }
        val gson : Gson by lazy { INSTANCE.gson }
    }

    fun getScreenWidth(): Float {
        return screenWidth
    }
    fun setScreenWidth(screenWidth: Float){
        this.screenWidth = screenWidth
    }
    fun getScreenHeight(): Float{
        return screenHeight
    }
    fun setScreenHeight(screenHeight : Float){
        this.screenHeight = screenHeight
    }
}