package codegen;

import codegen.builder.AbstractCodeBuilder;
import codegen.builder.ServiceCodeBuilder;
import codegen.model.EntityEnumModel;
import codegen.model.EntityModel;
import codegen.model.MemberAnnotationModel;
import codegen.model.MemberModel;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CodeGen {

    private final EntityModel entityModel;
    private final List<CodeGenType> types;
    private final Map<String, EntityEnumModel> enumMappings = Maps.newHashMap();
    private final Map<CodeGenType, List<AbstractCodeBuilder>> builders = Maps.newHashMap();
    private final VelocityEngine templateEngine;
    private final JavaParser javaParser = new JavaParser(new ParserConfiguration());

    int a=0;

    public CodeGen(Path path, List<CodeGenType> types) {

        this.entityModel = parse(path);

        this.types = types;
        templateEngine = new VelocityEngine();
        templateEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        templateEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        builders.put(CodeGenType.SERVICE, Lists.newArrayList(new ServiceCodeBuilder(entityModel, templateEngine)));

    }

    public EntityModel parse(Path path) { // args 의 entity VO 를 가지고 path를 분석하여 파일을 만들어 준다..

        try {
            if (!path.toFile().exists()) {    // 경로에 entity.java 파일이 존재하지 않는다면
                throw new RuntimeException("missing file , path =" + path);
            }

            CompilationUnit compilationUnit = javaParser.parse(new String(Files.readAllBytes(path)));

            /*
            *   entity VO 의 import 를 java parser 를 통해 읽어 들인다.
            * */
            Map<String, String> imports = Maps.newHashMap();
            for (ImportDeclaration anImport : compilationUnit.getImports()) {
                Name value = (Name) anImport.getChildNodes().get(0);
                String fullName = value.asString();
                System.out.println("entity import  --> " +fullName);
                int index = fullName.lastIndexOf(".");
                if (index > 0) {
                    imports.put(fullName.substring(index + 1), fullName.substring(0, index));
                }
            }
            EntityModel entityModel = new EntityModel();
            entityModel.fileName = path.getFileName().toString();   //path 상의 파일 full name
            entityModel.type = compilationUnit.getTypes().get(0).getName().toString();  //path 상의 파일 name
            entityModel.packageName = compilationUnit.getPackageDeclaration().get().getName().toString();   //path 상의 파일 pakage 경로
            entityModel.name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, entityModel.type); //path 상의 파일 name을 camel로 바꿔줌
            entityModel.shortName = shortName(entityModel.name);

            int index = entityModel.packageName.indexOf(".domain");
            if (index > 0) {
                entityModel.basePackage = entityModel.packageName.substring(0, index);  //파일의 부모 패키지
                entityModel.basePath = path.getParent().getParent();    //파일의 부모패키지 경로
            } else {
                entityModel.basePackage = entityModel.packageName;
                entityModel.basePath = path.getParent();
            }

            entityModel.path = path;    //파일의 경로
            entityModel.members = Lists.newArrayList(); //파일의 변수 및 모든 정보들을 담을 리스트 하나 작성

            Path rootPath = rootPath(path, entityModel.packageName); // root 경로를 찾아감 여기서는 src/main/java


            /*
            *       fieldDeclaration 이라는 것이 java 파일의 어노테이션을 포함한
            *       하나의 맴버 변수를 가져온다.
            *       예를들어 맴버 변수가 5개가 있다면 visit은 5번 돌면서 모든 맴버 값들을 가져온다.
             * */
            VoidVisitor<Void> visitor = new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(FieldDeclaration n, Void arg) {

                    MemberModel memberModel = new MemberModel();
                    Optional<AnnotationExpr> idAnnotation = n.getAnnotationByName("Id");
                    memberModel.type = n.getCommonType().toString();
                    memberModel.name = n.getVariable(0).getNameAsString();
                    /*memberModel.packageName = imports.get(memberModel.type);
                    if (memberModel.packageName == null && isSamePackage(entityModel.path.getParent(), memberModel.type)) {
                        memberModel.packageName = entityModel.packageName;
                    }
                    memberModel.isId = idAnnotation.isPresent();
                    memberModel.isIdGenerated = n.getAnnotationByName("GeneratedValue").isPresent();
                    Optional<EntityEnumModel> enumModel = parseEnum(rootPath, memberModel);
                    if (enumModel.isPresent()) {
                        memberModel.isEnum = true;
                        enumMappings.put(enumModel.get().type, enumModel.get());
                    } else {
                        memberModel.isEnum = false;
                    }
                    memberModel.inputType = inputType(memberModel.type, memberModel.length);

                    Optional<AnnotationExpr> columnOptional = n.getAnnotationByName("Column");
                    if (columnOptional.isPresent()) {
                        AnnotationExpr column = columnOptional.get();
                        for (Node childNode : column.getChildNodes()) {
                            if (childNode instanceof MemberValuePair) {
                                MemberValuePair valuePair = (MemberValuePair) childNode;
                                if (valuePair.getName().asString().equals("length")) {
                                    memberModel.length = Integer.parseInt(valuePair.getValue().toString());
                                    break;
                                }
                            }
                        }
                    }
                    Set<String> excludes = Sets.newHashSet("Column", "Id", "GeneratedValue", "Enumerated");
                    List<MemberAnnotationModel> annotationModels = Lists.newArrayList();
                    for (AnnotationExpr annotation : n.getAnnotations()) {
                        if (excludes.contains(annotation.getName().toString())) {
                            continue;
                        }
                        MemberAnnotationModel annotationModel = new MemberAnnotationModel();
                        annotationModel.type = annotation.getName().toString();
                        annotationModel.definition = annotation.toString();
                        annotationModel.packageName = imports.get(annotationModel.type);
                        if (annotationModel.packageName == null && isSamePackage(entityModel.path.getParent(), annotationModel.type)) {
                            annotationModel.packageName = entityModel.packageName;
                        }
                        annotationModel.isConstraints = annotationModel.packageName.contains(".constraints");
                        annotationModel.values = Maps.newHashMap();
                        for (Node childNode : annotation.getChildNodes()) {
                            if (childNode instanceof MemberValuePair) {
                                MemberValuePair valuePair = (MemberValuePair) childNode;
                                annotationModel.values.put(valuePair.getName().toString(), valuePair.getValue().toString());
                            }
                        }
                        annotationModels.add(annotationModel);
                    }
                    memberModel.annotations = annotationModels;
                    if (memberModel.isId) {
                        entityModel.id = memberModel;
                    } else {
                        entityModel.members.add(memberModel);
                    }*/
                }
            };

            compilationUnit.accept(visitor, null);  // 맴버 변수를 추출하기 위해 parser에 visitor 설정

            System.out.println("Test -->" +entityModel );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return entityModel;
    }

    Path rootPath(Path path, String packageName) {
        Path current = path.getParent();
        Iterator<String> iterator = Splitter.on(".").splitToList(packageName).iterator();
        while (iterator.hasNext() && current != null) {
            iterator.next();
            current = current.getParent();
            System.out.println("aa" + current);
        }
        return current;
    }


    private String shortName(String typeName){
        StringBuilder sb = new StringBuilder(typeName);

        for(int i = 0; i< typeName.length() ; i++){
            if(Character.isUpperCase(typeName.charAt(i))){
                sb.setCharAt(i,Character.toLowerCase(typeName.charAt(i)));
                sb.delete(0,i);
            }
        }

        return sb.toString();
    }


    public enum CodeGenType {
        SERVICE, API, IMPL, ADMIN, WEB, REACT, I18N
    }
}
