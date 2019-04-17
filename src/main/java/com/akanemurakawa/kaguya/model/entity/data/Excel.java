package com.akanemurakawa.kaguya.model.entity.data;

import java.io.Serializable;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * 实现对Excel数据的封装
 */
@Data
public class Excel implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 列头（标题）名
     */
	private String headTextName;

    /**
     * 对应字段名，需要执行的方法名称。Java反射机制
     */
    private String propertyName;

    /**
     * 合并单元格数
     */
    private Integer cols;

    /**
     * 表格 样式 2007+
     */
    private XSSFCellStyle cellStyle;

    public Excel(String headTextName, String propertyName){
        this.headTextName = headTextName;
        this.propertyName = propertyName;
    }
    
    public Excel(String headTextName, String propertyName, Integer cols) {
        super();
        this.headTextName = headTextName;
        this.propertyName = propertyName;
        this.cols = cols;
    }
}

