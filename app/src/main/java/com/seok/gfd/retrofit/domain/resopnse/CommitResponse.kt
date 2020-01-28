package com.seok.gfd.retrofit.domain.resopnse

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CommitResponse {
    var user_url: String = ""

    var data_date: String = ""

    var data_count: Int = 0

    var user_image: String = ""

    var user_id: String = ""

}