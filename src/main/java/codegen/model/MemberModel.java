package codegen.model;

import java.util.List;

/**
 *  parser를 이용하여 pojo 객체의 맴버 정보들을 담는 VO
 */
public class MemberModel {
    public Boolean isId;    //entity 클래스의 어노테이션이 @id 인지 아닌지
    public Boolean isIdGenerated;
    public Boolean isEnum;
    public String inputType;
    public String packageName;
    public String name; // 맴버변수의 이름
    public String type; // 맴버변수의 타입 ex)String,int
    public Integer length = 255;
    public List<MemberAnnotationModel> annotations;

    public Boolean getId() {
        return isId;
    }

    public Boolean getIdGenerated() {
        return isIdGenerated;
    }

    public Boolean getEnum() {
        return isEnum;
    }

    public String getInputType() {
        return inputType;
    }

    public Boolean getIsId() {
        return isId;
    }

    public Boolean getIsIdGenerated() {
        return isIdGenerated;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    public List<MemberAnnotationModel> getAnnotations() {
        return annotations;
    }

    @Override
    public String toString() {
        return "========== Member Model =========\n" +
                "isId -> ["+isId +"]\n " +
                "isIdGenerated --> [" + isIdGenerated+"]\n " +
                "isEnum --> [" + isEnum+"]\n " +
                "inputType --> [" + inputType+"]\n " +
                "packageName --> [" + packageName+"]\n " +
                "type --> [" + type+"]\n " +
                "name --> [" + name +"]\n " +
                "length --> [" + length+"]\n "+
                "annotations --> [" + annotations+"]\n "+
                "====================================\n";
    }
}
