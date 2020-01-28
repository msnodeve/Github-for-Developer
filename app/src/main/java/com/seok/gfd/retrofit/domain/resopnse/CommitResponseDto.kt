package com.seok.gfd.retrofit.domain.resopnse

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CommitResponseDto {
    var success: Boolean = false

    var code: Int = 0

    var msg: String? = null

    var data: Data? = null

    class Data {
        var content: List<CommitResponse>? = null
    }
}