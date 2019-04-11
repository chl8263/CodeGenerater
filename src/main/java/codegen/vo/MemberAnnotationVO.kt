package codegen.vo

import java.util.*

data class MemberAnnotationVO (

        var type : String ,
        var definition : String,
        var pakagename : String,
        var isConstraints : String,
        var values : Map<String, Objects>

)