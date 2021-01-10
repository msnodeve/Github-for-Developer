package com.seok.gfd.utils

class ValidationCheck {
    companion object {
        fun validIsEmptyString(str: String): Boolean {
            return str.isEmpty() || str == ""
        }

        fun isExistSite(str: String): Boolean{
            val url = "https://github.com/$str"



            return true
        }
    }
}