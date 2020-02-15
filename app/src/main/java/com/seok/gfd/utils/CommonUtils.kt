package com.seok.gfd.utils

class CommonUtils private constructor(){
    private object INSTANCE{
        val instance = CommonUtils()
    }

    private var screenWidth = 0f
    private var screenHeight = 0f

    companion object{
        val instance : CommonUtils by lazy { INSTANCE.instance }
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