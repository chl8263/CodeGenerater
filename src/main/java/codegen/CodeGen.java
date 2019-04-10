package codegen;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.google.common.collect.Maps;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.checkerframework.checker.units.qual.A;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CodeGen {

    private final EntityDTO entityDTO;
    private final List<CodeGenType> types;
    private final Map<String , EntityEnumDTO> enumMappings = Maps.newHashMap();
    private final Map<CodeGenType , List<AbstractCodeBuilder>> builders = Maps.newHashMap();
    private final VelocityEngine templateEngine;
    private final JavaParser javaParser = new JavaParser(new ParserConfiguration()); // 파싱하는데 뭐하는애인지 구체적으로 알 길이 없음..

    public CodeGen(Path path , List<CodeGenType> types){

        this.entityDTO = null;
        this.types =types;
        templateEngine = new VelocityEngine();
        templateEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        templateEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());



    }


    public enum CodeGenType{
        SERVICE, API, IMPL , ADMIN , WEB , REACT , I18N
    }
}
