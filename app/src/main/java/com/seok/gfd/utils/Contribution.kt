package com.seok.gfd.utils

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Contribution {
    var year : String? = null
    var total : Int? = 0
    var list : ArrayList<ContributionInfo>? = ArrayList()

    class ContributionInfo(date : String, count: Int, color : String, intensity: Int){
        var date : String = date
        var count : Int = count
        var color : String = color
        var intensity : Int = intensity
    }
}