package com.seok.gfd.retrofit.domain.resopnse

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class UserCount {
    var success: String = ""
    var code: Int = 0
    var msg: String = ""
    var data :Long = 0
}