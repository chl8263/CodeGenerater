package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFactory {

    private static Logger logger;

    public static Logger getLogger(Class<?> clazz){
        logger = LoggerFactory.getLogger(clazz);

        return logger;
    }

    public void error(String msg){
        logger.error(msg);
    }
}
