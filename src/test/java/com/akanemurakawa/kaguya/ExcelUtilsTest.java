package com.akanemurakawa.kaguya;

import com.akanemurakawa.kaguya.util.ExcelUtils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelUtilsTest {
	
	public void testImportExcel() throws Exception {
		ExcelUtils excel = new ExcelUtils();
		String fileName = "xxxxxxx.xlsx";
		InputStream inputStream = new FileInputStream(fileName);
		int startRow = 1;
		System.out.println("开始测试");
		List<List<Object>> result = 
				excel.importExcel(inputStream, fileName, startRow);
		for (int i = 0; i < result.size() ; i++) {
			List<Object> list = result.get(i);
			for (int j = 0; j < list.size(); j++) {
				System.out.print(list.get(j)+"\t");
			}
			System.out.println();
		}
		System.out.println("测试完毕");
	}
}
