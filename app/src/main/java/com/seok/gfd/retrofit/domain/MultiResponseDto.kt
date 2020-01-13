package com.seok.gfd.retrofit.domain

class MultiResponseDto<T>: CommonResponseDto() {
    var data: List<T>? = null
}