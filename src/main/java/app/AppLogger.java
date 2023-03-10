package app;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 插件类
 * 
 * 日志默认位置 $USER_HOME/.mks/PluginLog
 * 
 */
public class AppLogger {

    private static FileHandler fileHandler = null;

    static {
        try {
            File dir = new File(System.getProperty("user.home") + "/.mks/PluginLog");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File logFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".log", dir);
            fileHandler = new FileHandler(logFile.getAbsolutePath());
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            fileHandler.setEncoding("UTF-8");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回日志记录组件
     * 
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class<?> clazz){
        Logger logger = Logger.getLogger(clazz.getName());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        return logger;
    }
}
