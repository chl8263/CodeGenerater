package codegen.builder;

import codegen.vo.EntityVO;
import codegen.vo.MemberVO;
import com.google.common.collect.Sets;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;
import java.util.Set;

public class ServiceCodeBuilder extends AbstractCodeBuilder {

    public ServiceCodeBuilder(EntityVO entityVO, VelocityEngine templateEngine) {
        super(entityVO, VelocityPath.adminUserServiceTemplatePath , templateEngine);
    }

    @Override
    public String build() {

        VelocityContext context = new VelocityContext();
        EntityVO entityVO = entityVO();
        context.put("entity" , entityVO);
        context.put("pakageName" , entityVO.getBasepakage()+".service");

        Set<String> importRefs = Sets.newHashSet();
        for(MemberVO vo : entityVO.getMembers()){

        }

        return null;
    }

    @Override
    public Path path() {
        return null;
    }
}
