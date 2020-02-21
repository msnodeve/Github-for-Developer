package com.seok.gfd.retrofit.domain

import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class YearContributionDto {
    var year: String? = null

    var contributions: ArrayList<Contribution>? = null

    constructor() {
        this.contributions = ArrayList()
    }

    class Contribution{
        var date : String = ""
        var count : Int = 0
        var color : String = ""
        var intensity : Int = 0
    }


}