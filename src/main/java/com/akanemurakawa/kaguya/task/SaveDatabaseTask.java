package com.akanemurakawa.kaguya.task;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 完整备份数据库
 */
public class SaveDatabaseTask {
	
	private boolean isFirstRun = true; // 是否是第一次运行

	public void SaveDatabase() {
		Properties p = new Properties();
		InputStream fis = null;
		String username = null; // 数据库用户名
		String password = null; // 数据库密码
		String host = null;     // 数据库ip
		String dbname = null;   // 数据库名
		String backupPath = null; // 备份的路径
		String backupFile = null;
		String mysql = null;
		try {
			// 读取配置文件 
			//fis = new FileInputStream("src/main/resources/load.properties");
			fis = this.getClass().getClassLoader().getResourceAsStream("load.properties");
			p.load(fis);
			username = p.getProperty("jdbc.username");
			password = p.getProperty("jdbc.password");
			host = p.getProperty("jdbc.host");
			dbname = p.getProperty("jdbc.dbname");
		} catch (Exception e) {
			e.printStackTrace();
		}
		backupFile = "./backupFreeblogsDB/freeblogs" +new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".sql";
		try {
			//System.out.println(System.getProperties().getProperty("os.name"));
			if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("LINUX") != -1) {
				// linux系统
				if (isFirstRun) {
					mysql = "cd /;mkdir backupFreeblogsDB; mysqldump -h " + host + " -u " + username + " -p" + password + 
							" " + dbname + " > " + backupFile;
					isFirstRun = false;
				}else {
					mysql = "cd /;mysqldump -h " + host + " -u " + username + " -p" + password + 
							" " + dbname + " > " + backupFile;
				}
				Runtime.getRuntime().exec(new String[] { "sh", "-c", mysql});
			}else {
				// windows系统
				// cmd /c dir 是执行完dir命令后关闭命令窗口。
				if (isFirstRun) {
					mysql = "cd / && mkdir backupFreeblogsDB && mysqldump -h " + host + " -u " + username + " -p" + password + 
							" " + dbname + " > " + backupFile;
					isFirstRun = false;
				}else {
					mysql = "cd / && mysqldump -h " + host + " -u " + username + " -p" + password + 
							" " + dbname + " > " + backupFile;

				}
				Runtime.getRuntime().exec("cmd /k" + mysql);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(mysql);
	}
}
