package com.materialize.jee.platform.codegen.tools;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.io.IOUtils;


public class ConnectUtil {
	
	private static class ConnectionHandler{
		private static Connection connect;
		static {
			FileInputStream input = null;
			try {
				String rootPath=System.getProperty("user.dir"); 
				input = new FileInputStream(rootPath+"/src/main/resources/jdbc.properties");
				Properties prop = new Properties();
				prop.load(input);
				input.close();
				String driveClass = prop.getProperty("jdbc.driverClass");
				String connectionURL = prop.getProperty("jdbc.url");
				String userId = prop.getProperty("jdbc.username");
				String password = prop.getProperty("jdbc.password");
				Class.forName(driveClass).newInstance();
				connect = DriverManager.getConnection(connectionURL,userId, password);
			} catch (Exception e) {
			    e.printStackTrace();
			}finally{
				IOUtils.closeQuietly(input);
			}
		}
	}
	public static Connection getConnection(){
		return ConnectionHandler.connect;
	}
	
	public static void closeConnection(Connection connect){
		if(connect!=null){
			try{
				connect.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
