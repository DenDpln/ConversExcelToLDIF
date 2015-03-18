package ConversExcelToLDIF;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LogProp {
    public static FileAppender updateLog4jConfiguration(String logFile){
        FileAppender appender = new FileAppender();
        appender.setName("src/log4j.properties");
        appender.setLayout(new PatternLayout("%d{ABSOLUTE} %5p %t %c{1}:%M:%L - %m%n"));
        appender.setFile(logFile);
        appender.setAppend(true);
        appender.setThreshold(Level.INFO);
        appender.activateOptions();
        Logger.getRootLogger().addAppender(appender);
        return appender;
    }
}

