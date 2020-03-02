package com.seok.gfd.retrofit.domain.resopnse

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class NestedContributionsResponseDto {

    var years: HashMap<Int, Years>? = null

    var contributions : Contributions? = null

    inner class Years{
        var year:  String? = null
        var total : Int? = 0
        var range: Range? = null

        inner class Range{
            var start: String = ""
            var end: String = ""
        }
    }

    inner class Contributions{
        var year: String? = null
        var total : Int? = 0
        var range: Range? = null
        var contributions : HashMap<Int, HashMap<Int, HashMap<Int, Contribution>>>? = null

        inner class Range{
            var start: String = ""
            var end: String = ""
        }

        inner class Contribution{
            var date : String = ""
            var count : Int = 0
            var color : String = ""
            var intensity : Int = 0
        }
    }
}