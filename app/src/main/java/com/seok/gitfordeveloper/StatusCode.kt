package com.seok.gitfordeveloper

import android.support.annotation.IntDef
import java.lang.annotation.RetentionPolicy

object StatusCode {

    const val REQUEST_CODE = "requestCode"

    // TypeDef - IntDef
    // Constants
    const val SUCCESS = 100
    const val FAIL = 101
    const val NONE = 2000
    const val REQUEST_GITHUB_SIGNIN = 1000
    const val REQUEST_GITHUB_SIGNOUT = 1001
    const val REQUEST_GITHUB_REDIRECT = 1002

}