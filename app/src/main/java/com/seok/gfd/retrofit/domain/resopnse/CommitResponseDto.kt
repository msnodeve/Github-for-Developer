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
    var users_id: Long = 0

    var url: String = ""

    var data_date: String = ""

    var data_count: Int = 0

    var profile_image: String = ""

    var uid: String = ""

    constructor(profile_image: String, dataCount: Int, uid: String){
        this.profile_image = profile_image
        this.data_count = dataCount
        this.uid = uid
    }
}