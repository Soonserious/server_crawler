package kr.ac.cbnu.ubigame.analyser.common;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class AnalyserLogger {	
	public static void setLoggerConf(String fileName, String maxFileSize, Level threshold) throws Exception {
		String pattern = "[%d{ISO8601}] %-5r [%t] %l : %-5p %-30c - %m%n";
		Logger rootLogger = Logger.getRootLogger();        
		Layout layout = new PatternLayout(pattern);         
		try {
			RollingFileAppender ra = new RollingFileAppender(layout, fileName);
			ra.setAppend(true);
			ra.setMaxFileSize(maxFileSize);
			ra.setThreshold(threshold);
			rootLogger.addAppender(ra);
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}
