import codegen.CodeGen;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import util.LogFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    //static Logger logger = LogFactory.getLogger(Main.class);



    public static void main(String [] args){

        List<CodeGen.CodeGenType> types;
        String entityPath;

        if(args.length ==1){
            types = Arrays.asList(CodeGen.CodeGenType.SERVICE, CodeGen.CodeGenType.API, CodeGen.CodeGenType.IMPL,
                    CodeGen.CodeGenType.ADMIN, CodeGen.CodeGenType.WEB, CodeGen.CodeGenType.REACT, CodeGen.CodeGenType.I18N);
            entityPath = args[0];
        }else if(args.length == 2){
            types = Splitter.on(',').splitToList(args[0]).stream().map(command -> CodeGen.CodeGenType.valueOf(command.toUpperCase()))
                    .collect(Collectors.toList());
            entityPath = args[1];
        }else {
            System.out.println("파라메터 값이 없습니다.");
            return ;
        }

        File entityFile;    // DTO 객체를 이용하여 CodeGen을 할 데이터 객체
        if(entityPath.startsWith(".")){
            Path dir = Paths.get(System.getProperty("user.dir"));
            entityFile = dir.resolve(entityPath).toFile();  //resolve 는 경로를 합치는데 사용
        } else {
            entityFile = new File(entityPath);
        }

        if(entityFile.isDirectory()){   //해당 패스에서 디렉토리(폴더)가 존재하는지 확인

            for(File file : entityFile.listFiles()){
                if(file.getName().endsWith(".java")){
                    CodeGen codeGen = new CodeGen(entityFile.toPath() , types);
                }
            }

        } else {
            System.out.println("낄낄"+entityFile.toPath());
            CodeGen codeGen = new CodeGen(entityFile.toPath(), types);
            //codeGen.generate();
        }

    }
}
