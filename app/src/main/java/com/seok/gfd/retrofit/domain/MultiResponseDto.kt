package com.seok.gfd.retrofit.domain

class MultiResponseDto<T>: CommonResponseDto() {
    var list: List<T>? = null
}