package com.seok.gitfordeveloper.retrofit.domain.resopnse

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class TRCommitResponseDto {
    var users_id: Long = 0

    var url: String = ""

    var data_date: String = ""

    var data_count: Int = 0

    var profile_image: String = ""

    var uid: String = ""

}