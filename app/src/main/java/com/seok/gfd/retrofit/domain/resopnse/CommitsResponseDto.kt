package com.seok.gfd.retrofit.domain.resopnse

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CommitsResponseDto {

    var years: List<Year>? = null

    var contributions: List<Contribution>? = null

    class Year{
        var year: String = ""
        var total: Int = 0
        var range : Range? = null

        class Range{
            var start: String = ""
            var end: String = ""
        }
    }

    class Contribution{
        var date : String = ""
        var count : Int = 0
        var color : String = ""
        var intensity : Int = 0
    }
}