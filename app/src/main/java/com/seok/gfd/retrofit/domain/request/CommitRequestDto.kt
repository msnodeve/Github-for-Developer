package com.seok.gfd.retrofit.domain.request

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CommitRequestDto {
    var userId: String = ""
    var dataCount: Int = 0

    constructor(userId: String, dataCount: Int) {
        this.userId = userId
        this.dataCount = dataCount
    }
}