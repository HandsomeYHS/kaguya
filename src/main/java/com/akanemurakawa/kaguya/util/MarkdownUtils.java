package com.akanemurakawa.kaguya.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@Slf4j
public class MarkdownUtils {
	
	/**
	 * 1.保存markdown文件
	 */
	public static int saveMarkdown(String markdownContent, String filename, String path){
		File mdFile = new File(path, filename+".md");
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		PrintWriter pw = null;	
		try {
			fos = new FileOutputStream(mdFile);
			osw = new OutputStreamWriter(fos, "UTF-8"); 
			pw = new PrintWriter(osw);
			pw.write(markdownContent);
		} catch (IOException e) {
		    log.error("保存markdown文件失败，error:{}", e);
		}finally{
			// 关闭流
			try {
				osw.close();
			} catch (IOException e) {
                log.error("保存markdown文件失败，error:{}", e);
			}
		}
		return 0;
	}

	/**
	 * 2.读取markdown文件
	 */
	public static String getMarkdown(String title, String path) {
		String markdownContent = "";
		StringBuffer sb = new StringBuffer();
		
		File file = new File(path, title+".md");
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String line = ""; // 每次读取一行
			while ((line =br.readLine()) != null) {
				sb.append(line).append("\n"); // 这里需要添加换行符号
			}
			markdownContent = sb.toString();
		} catch (IOException e) {
            log.error("读取markdown文件失败，error:{}", e);
		}finally{
			// 关闭流
			try {
				fis.close();
			} catch (IOException e) {
                log.error("读取markdown文件失败，error:{}", e);
			}
		}
		if (null == markdownContent) {
			markdownContent = "未找到markdown文件";
		}
		return markdownContent;
	}

	/**
	 * 3.删除markdown文件
	 */
	public static int removeMarkdown(String title, String path) {
		File file = new File(path, title+".md");
		try {
			file.delete();
		} catch (Exception e) {
            log.error("删除markdown文件失败，error:{}", e);
        }
		return 0;
	}

}
