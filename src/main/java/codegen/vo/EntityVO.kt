package codegen.vo

import java.nio.file.Path

data class EntityVO(

        var path : Path,
        var basePath : Path,
        var group : String = "",
        var basepakage : String,
        var fileName : String,
        var type : String,
        var name : String,
        var shortName : String,
        var pakageName : String,
        var enumtype : List<EntityEnumVO>,

        var id : MemberVO,
        var members : List<MemberVO>


        )