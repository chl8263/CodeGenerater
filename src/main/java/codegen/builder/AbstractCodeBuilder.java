package codegen.builder;

import codegen.vo.EntityVO;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;

public abstract class AbstractCodeBuilder {

    private final EntityVO entityVO;
    private final String templatePath;
    private final VelocityEngine templateEngine;

    public AbstractCodeBuilder(EntityVO entityVO, String templatePath, VelocityEngine templateEngine) {
        this.entityVO = entityVO;
        this.templatePath = templatePath;
        this.templateEngine = templateEngine;
    }

    public abstract String build();

    public abstract Path path();

    protected  VelocityEngine templateEngine () {
        return templateEngine;
    }

    protected Template template(){
        return templateEngine.getTemplate(templatePath);
    }

    protected EntityVO entityVO (){
        return entityVO;
    }
}

