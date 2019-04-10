package codegen

import java.nio.file.Path

data class EntityDTO(

        var path : Path,
        var basePath : Path,
        var group : String = "",
        var basepakage : String,
        var fileName : String,
        var type : String,
        var name : String,
        var shortName : String,
        var pakageName : String,
        var enumtype : List<EntityEnumDTO>,

        var id : MemberDTO,
        var members : List<MemberDTO>


        )