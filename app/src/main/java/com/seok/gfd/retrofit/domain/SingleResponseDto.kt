package com.seok.gfd.retrofit.domain

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class SingleResponseDto<T> : CommonResponseDto() {
    var data: T? = null
}