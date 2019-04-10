package codegen;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;

public abstract class AbstractCodeBuilder {

    private final EntityDTO entityDTO;
    private final String templatePath;
    private final VelocityEngine templateEngine;

    public AbstractCodeBuilder(EntityDTO entityDTO, String templatePath, VelocityEngine templateEngine) {
        this.entityDTO = entityDTO;
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

    protected  EntityDTO entityDTO (){
        return entityDTO;
    }
}

