package com.github.codegen.code;

import com.github.codegen.util.DBConnection;
import com.github.codegen.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成助手类

 */
public class CodeGenHelper {
	
	/** 定义查询表的SQL语句 */
	private static final String QUERY_SQL = "SELECT table_name FROM information_schema.TABLES WHERE table_schema = ?";
	/** 数据库表的前缀 */
	private String tablePrefix = "tb_";
	// 数据库操作对象
	private DBConnection dbConnection;
	// 数据库名称
	private String dbName;
	/** 构造器 */
	public CodeGenHelper(DBConnection dbConnection, String dbName){
		this.dbConnection = dbConnection;
		this.dbName = dbName;
	}
	/** 生成代码 */
	public void generatorCode(String path, String packageName, int codeType,String authorName,String emailName) throws Exception{
		Connection conn = dbConnection.getConnection("information_schema");
		PreparedStatement pstmt = conn.prepareStatement(QUERY_SQL);
		pstmt.setString(1, dbName);
		ResultSet rs = pstmt.executeQuery();
		// 保存该数据库中所有表名
		List<String> tables = new ArrayList<>();
		while (rs.next()){
			tables.add(rs.getString(1));
		}
		// 循环所有的表 
		for (String tableName : tables){
			// 操作一张表
			pstmt = conn.prepareStatement("SELECT * FROM " + dbName + "." + tableName + " LIMIT 0,1");
			ResultSetMetaData rd = pstmt.getMetaData();
			// 保存一张表的列名与列的类型信息
			Map<String, Integer> columns = new LinkedHashMap<>();
			for (int i = 0; i < rd.getColumnCount(); i++){
				columns.put(rd.getColumnName(i + 1), rd.getColumnType(i + 1));
			}
			
			/** 通过表名生成DTO的类名 */
			String className = tableName.toLowerCase().replace(tablePrefix.toLowerCase(), ""); // 替换掉表的前缀
			/** 将类名中有"_"符号隔开的单词首字母大写 */
			String[] names = className.split("_"); // 将所有用_分隔的首字母大写
			className = StringUtils.transferUpper(names[0]);
			for (int i = 1; i < names.length; i++){
				className += StringUtils.transferUpper(names[i]);
			}

			if (codeType == 1){
				// 得到一张表信息后，生成对应的实体
				PojoGenerator.generatorPojo(path + "/src/main/java/", className, packageName, columns,authorName,emailName);
			}else if(codeType == 2){
				// 写出该表对应的MAPPER接口与映射文件
				MapperGenerator.generatorMapper(path, className, packageName,authorName,emailName);
			}else if(codeType == 3){
				// 生成业务层接口与实现类
				ServiceGenerator.generatorService(path + "/src/main/java/", className, packageName,authorName,emailName);
			}else if (codeType == 4){
				// 生成控制器类
				ControllerGenerator.generatorController(path + "/src/main/java/", className, packageName,authorName,emailName);
			}else if (codeType == 5){
				// 生成前端AngularJS
				AngularGenerator.generatorMVC(path, className);
			}else if (codeType == 5){
				// 得到一张表信息后，生成对应的实体类【Spring注解绑定】
				PojoGenerator.generatorPojoSpring(path + "/src/main/java/", className,tableName, packageName, columns,authorName,emailName);
			}
		}
	}
	
	/** setter and getter method */
	public String getTablePrefix() {
		return tablePrefix;
	}
	public void setTablePrefix(String tablePrefix) {
		if (tablePrefix == null || "".equals(tablePrefix)){
			tablePrefix = "";
		}
		this.tablePrefix = tablePrefix;
	}
}