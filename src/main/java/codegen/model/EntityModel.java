package codegen.model;

import java.nio.file.Path;
import java.util.List;

public class EntityModel {
    public Path path;//파일의 경로
    public Path basePath;//파일의 부모패키지 경로
    public String group = "";
    public String basePackage;//파일의 부모 패키지
    public String fileName;//path 상의 파일 full name
    public String type;//path 상의 파일 name
    public String name;//path 상의 파일 name을 camel로 바꿔줌
    public String shortName;
    public String packageName;//path 상의 파일 pakage 경로
    public List<EntityEnumModel> enumTypes;

    public MemberModel id;
    public List<MemberModel> members;//파일의 변수 및 모든 정보들을 담을 리스트 하나 작성

    public String getGroup() {
        return group;
    }

    public List<EntityEnumModel> getEnumTypes() {
        return enumTypes;
    }

    public String getName() {
        return name;
    }

    public MemberModel getId() {
        return id;
    }

    public Path getPath() {
        return path;
    }

    public Path getBasePath() {
        return basePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getType() {
        return type;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getShortName() {
        return shortName;
    }

    public List<MemberModel> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "========== Entity Model =========\n" +
                "file name -> ["+fileName +"]\n " +
                "path --> [" + path+"]\n " +
                "basePath --> [" + basePath+"]\n " +
                "group --> [" + group+"]\n " +
                "basePackage --> [" + basePackage+"]\n " +
                "type --> [" + type+"]\n " +
                "name --> [" + name +"]\n " +
                "shortName --> [" + shortName+"]\n "+
                "packageName --> [" + packageName+"]\n "+
                "enumTypes --> [" + enumTypes+"]\n "+
                "id --> [" + id+"]\n "+
                "members --> [" + members+"]\n" +
                "====================================\n";
    }
}

