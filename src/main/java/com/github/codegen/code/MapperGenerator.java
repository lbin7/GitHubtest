package com.github.codegen.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * MyBatis MAPPER接口与映射文件生成器

 */
public class MapperGenerator {
	
	/** 日期格式化对象 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 生成MAPPER接品与映射文件
	 * @param className
	 * @param mapperPackage
	 */
	public static void generatorMapper(String path, String className, String mapperPackage,String authorName,String emailName){
		try{

			String basePackage = mapperPackage.substring(0, mapperPackage.lastIndexOf("."));
			/** ####################### 生成接口类 ###################### **/
			/** 定义StringBuider来拼接生成接口类的字符串 */
			StringBuilder res = new StringBuilder();
			res.append("package " + mapperPackage + ";\n\n");
			res.append("import tk.mybatis.mapper.common.Mapper;\n\n");
			res.append("import "+ basePackage +".pojo."+ className +";\n\n");
			
			/** 拼接生成注释字符串 */
			res.append("/**\n");
			res.append(" * " + className + "Mapper 数据访问接口\n");
			if (authorName!=null&&!authorName.equals("")){
				res.append(" * @author " + authorName + "\n");
			}
			if (emailName!=null&&!emailName.equals("")){
				res.append(" * @email" + emailName + "\n");
			}
			res.append(" * @date " + sdf.format(Calendar.getInstance().getTime()) + "\n");
			res.append(" * @version 1.0\n");
			res.append(" */\n");
			
			/** 拼接生成接口类的字符串 */
			res.append("public interface " + className + "Mapper extends Mapper<"+ className +">{\n\n");
			res.append("\n\n}");
			
			/** 将包名替换成相应的文件目录结构字符串 */
			File file = new File(path + "/src/main/java/" + mapperPackage.replaceAll("\\.", "/"));
			/** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
			if (!file.exists()) file.mkdirs();
			/** 在该目录下创建生存相关的DTO Java文件 */
			file = new File(path + "/src/main/java/" + mapperPackage.replaceAll("\\.", "/") + File.separator + className + "Mapper.java");
			/** 创建FileWriter输出流，将拼接好的Java源文件输出 */
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			osw.write(res.toString());
			osw.flush();
			osw.close();

			
			/** ####################### 生成SQL映射文件  ###################### **/
			/** 定义StringBuider来拼接生成SQL映射文件的字符串 */
			res = new StringBuilder();
			res.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
			   .append("<!DOCTYPE mapper\n")
			   .append("\tPUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n")
			   .append("\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n\n")
			   .append("<mapper namespace=\""+ mapperPackage +"."+ className +"Mapper\">\n\n")
			   .append("</mapper>");
			/** 将包名替换成相应的文件目录结构字符串 */
			file = new File(path + "/src/main/resources/mappers/");
			/** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
			if (!file.exists()) file.mkdirs();
			/** 在该目录下创建生存相关的DTO Java文件 */
			file = new File(path + "/src/main/resources/mappers/" + className + "Mapper.xml");
			/** 创建FileWriter输出流，将拼接好的Java源文件输出 */
			osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			osw.write(res.toString());
			osw.flush();
			osw.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}