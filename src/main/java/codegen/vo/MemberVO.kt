package codegen.vo

data class MemberVO (

        var isID : Boolean,
        var isIdGenerated : Boolean,
        var isEnum : Boolean,
        var inputType : String,
        var pakageName : String ,
        var name : String,
        var type : String,
        var length : Int = 255,

        var annotations : List<MemberAnnotationVO>

)