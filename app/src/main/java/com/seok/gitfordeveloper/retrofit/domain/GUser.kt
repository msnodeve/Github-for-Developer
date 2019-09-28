package com.seok.gitfordeveloper.retrofit.domain

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class GUser {
    var userId: String = ""

    var userUrl: String = ""

    var profileImage: String = ""

    constructor(userId : String, userUrl : String, profileImage: String){
        this.userId = userId
        this.userUrl = userUrl
        this.profileImage = profileImage
    }
}