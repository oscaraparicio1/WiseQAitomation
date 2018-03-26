package es.sogeti.wiseqarest.configuration;

import java.io.File;

public class Environment {

	private Environment(){}
	
	/**
	 * FOLDER VARIABLES
	 */
	public static final String VERSION = "18.0";
	public static final String SPSS_ROOT = File.separator + "usr" + File.separator + "IBM" + File.separator + "SPSS" + File.separator;
	public static final String MODELER_SERVER = SPSS_ROOT + "ModelerServer" + File.separator + VERSION + File.separator;
	public static final String MODELER_BATCH = SPSS_ROOT + "ModelerBatch" + File.separator + VERSION + File.separator;
	public static final String STREAM_DATA_FOLDER = MODELER_BATCH + "data" + File.separator;
	public static final String R_SCRIPTS_FOLDER = File.separator + "usr" + File.separator + "local" + File.separator + "tomcat" + File.separator + "scripts" + File.separator;
	public static final String R_WISEQA_FOLDER = R_SCRIPTS_FOLDER + "R-WiseQA" + File.separator;
	public static final String R_DATA_FOLDER = R_WISEQA_FOLDER + File.separator + "data" + File.separator;
	
}
