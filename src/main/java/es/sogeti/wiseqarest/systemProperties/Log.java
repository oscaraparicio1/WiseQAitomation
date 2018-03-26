package es.sogeti.wiseqarest.systemProperties;

import org.apache.log4j.Logger;

public class Log {

	private Log(){}
	
	public static void info(Exception e){
		Logger logger = Logger.getLogger(e.getClass().toString());
		logger.info(e.getMessage(), e);
	}
	
	public static void info(Class cls, String s){
		Logger logger = Logger.getLogger(cls.toString());
		logger.info(s);
	}
	
	public static void warn(Exception e){
		Logger logger = Logger.getLogger(e.getClass());
		logger.warn(e.getMessage(), e);
	}
	
	public static void warn(Class cls, String s){
		Logger logger = Logger.getLogger(cls.toString());
		logger.warn(s);
	}	
	
	public static void debug(Exception e){
		Logger logger = Logger.getLogger(e.getClass());
		logger.debug(e.getMessage(), e);
	}

	public static void debug(Class cls, String s){
		Logger logger = Logger.getLogger(cls.toString());
		logger.debug(s);
	}	
	
	public static void error(Exception e){
		Logger logger = Logger.getLogger(e.getClass());
		logger.error(e.getMessage(), e);
	}
	
	public static void error(Class cls, String s){
		Logger logger = Logger.getLogger(cls.toString());
		logger.error(s);
	}		
}
