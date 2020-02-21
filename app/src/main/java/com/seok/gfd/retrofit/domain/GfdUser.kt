package com.seok.gfd.retrofit.domain

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class GfdUser {
    var userId: String = ""

    var userUrl: String = ""

    var userImage: String = ""

    constructor(userId : String, userUrl : String, userImage: String){
        this.userId = userId
        this.userUrl = userUrl
        this.userImage = userImage
    }
}