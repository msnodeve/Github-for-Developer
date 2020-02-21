package com.seok.gfd.retrofit.domain

import lombok.Getter
import lombok.Setter

@Setter
@Getter
open class CommonResponseDto {
    var success: Boolean? = false

    var code : Int? = 0

    var msg : String? = ""
}