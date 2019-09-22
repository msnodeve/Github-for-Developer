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
    private val id : Long

    private val userId: String

    private val userGId: String

    private val userUrl: String

    private val profileImage: String

    private val createDate: String

    constructor(userId : String, userGid : String, userUrl : String, profileImage: String){

    }
}