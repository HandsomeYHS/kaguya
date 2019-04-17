package com.akanemurakawa.kaguya.service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import com.akanemurakawa.kaguya.model.entity.Admin;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

	Admin checkAdminLogin(Admin admin);
	
	boolean importExcelUserInfo(InputStream in, MultipartFile file);
	
	void exportExcelUserInfo(OutputStream outputStream, String sheetName) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException, IllegalArgumentException, IOException;
}

