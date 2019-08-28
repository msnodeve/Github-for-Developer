package com.seok.gitfordeveloper.retrofit.domain

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
class Token {
    var access_token : String = ""
    var scope : String = ""
    var token_type : String = ""
    var code : Int = 0
    constructor(accessToken : String, scope: String, tokenType : String, code : Int){
        this.access_token = accessToken
        this.scope = scope
        this.token_type = tokenType
        this.code = code
    }
    constructor(code : Int){
        this.code = code
    }
}