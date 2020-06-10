package com.fabella;

public class CONSTANTS {

	public final static String DRIVER_CLASS = Utils.getValueByNodeName("config.xml", "driverClass");
	public final static String CONNECTION_URL = Utils.getValueByNodeName("config.xml", "connectionUrl");
	public final static String DB_USERNAME = Utils.getValueByNodeName("config.xml", "db_UserName");
	public final static String DB_PASSWORD = Utils.getValueByNodeName("config.xml", "db_Password");
	public final static String DB_NAME = Utils.getValueByNodeName("config.xml", "db_Name");
	public final static String DELEMITER = "||";
	public final static String DELEMITER_ARROW = "->"; 
	
	
}
