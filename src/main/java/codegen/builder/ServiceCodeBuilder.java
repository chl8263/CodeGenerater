package codegen.builder;

import codegen.model.EntityModel;
import codegen.model.MemberModel;
import com.google.common.collect.Sets;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;
import java.util.Set;

public class ServiceCodeBuilder extends AbstractCodeBuilder {

    public ServiceCodeBuilder(EntityModel entityModel, VelocityEngine templateEngine) {
        super(entityModel, VelocityPath.adminUserServiceTemplatePath , templateEngine);
    }

    @Override
    public String build() {

        VelocityContext context = new VelocityContext();
        EntityModel entityModel = new EntityModel();
        context.put("entity" , entityModel);
        context.put("pakageName" , entityModel.basePackage+".service");

        Set<String> importRefs = Sets.newHashSet();
        for(MemberModel vo : entityModel.getMembers()){

        }

        return null;
    }

    @Override
    public Path path() {
        return null;
    }
}
