package codegen.model;

import java.util.List;

/**
 * @author chi
 */
public class MemberModel {
    public Boolean isId;
    public Boolean isIdGenerated;
    public Boolean isEnum;
    public String inputType;
    public String packageName;
    public String name;
    public String type;
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
        return "========== Entity Model =========\n" +
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
