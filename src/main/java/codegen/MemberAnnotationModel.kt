package codegen

import java.util.*

data class MemberAnnotationModel (

        var type : String ,
        var definition : String,
        var pakagename : String,
        var isConstraints : String,
        var values : Map<String, Objects>

)