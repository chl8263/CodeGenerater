package codegen;

import codegen.builder.AbstractCodeBuilder;
import codegen.builder.ServiceCodeBuilder;
import codegen.vo.EntityEnumVO;
import codegen.vo.EntityVO;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CodeGen {

    private final EntityVO entityVO;
    private final List<CodeGenType> types;
    private final Map<String , EntityEnumVO> enumMappings = Maps.newHashMap();
    private final Map<CodeGenType , List<AbstractCodeBuilder>> builders = Maps.newHashMap();
    private final VelocityEngine templateEngine;
    private final JavaParser javaParser = new JavaParser(new ParserConfiguration()); // 파싱하는데 뭐하는애인지 구체적으로 알 길이 없음..

    public CodeGen(Path path , List<CodeGenType> types){

        this.entityVO = parse(path);

        this.types =types;
        templateEngine = new VelocityEngine();
        templateEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        templateEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        builders.put(CodeGenType.SERVICE , Lists.newArrayList(new ServiceCodeBuilder(entityVO , templateEngine)));

    }

    public EntityVO parse( Path path){ // args 의 entity VO 를 가지고 path를 분석하여 파일을 만들어 준다..
        if(!path.toFile().exists()){    // 경로에 entity.java 파일이 존재하지 않는다면
            throw  new RuntimeException("missing file , path =" + path);
        }



    }


    public enum CodeGenType{
        SERVICE, API, IMPL , ADMIN , WEB , REACT , I18N
    }
}
