package com.github.codegen.code;

import com.github.codegen.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

/**
 * DTO Java File Helper

 */
public final class PojoGenerator {
	
	/** 日期格式化对象 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Random random=new Random();
	/**
	 * 生成对应的Pojo源文件
	 * @param path 项目存储路径
	 * @param className 类名
	 * @param packageName 生成的包名
	 * @param columns 列的集合
	 */
	public static void generatorPojo(String path, String className,
					String packageName, Map<String, Integer> columns) {
		try{
			
			/** 定义StringBuider来拼接生成类的字符串 */
			StringBuilder res = new StringBuilder();
			res.append("package " + packageName + ";\n\n");
			
			/** 拼接生成注释字符串 */
			res.append("/**\n");
			res.append(" * " + className + " 实体类\n");
			res.append(" * @date " + sdf.format(Calendar.getInstance().getTime()) + "\n");
			res.append(" * @version 1.0\n");
			res.append(" */\n");

			/** 拼接生成类的字符串 */
			res.append("public class " + className + " implements java.io.Serializable{\n\n");
			res.append("\tprivate static final long serialVersionUID = 1L;\n");
			/** 生成DTO类中相关属性 */
			for (Map.Entry<String, Integer> map : columns.entrySet()){
				/** 处理数据类型(数据库的数据类型转化成Java相关类型) */
				String fieldType = toJavaType(map.getValue());
				/** 定义属性名： 将数据库表中的列名全部转换成小写字母，作为属性名 */
				String fieldName = map.getKey().toLowerCase();
				/** 将属属中有"_"符号隔开的单词首字母大写  */
				String[] fields = fieldName.split("_");
				fieldName = fields[0];
				for (int i = 1; i < fields.length; i++){
					fieldName += StringUtils.transferUpper(fields[i]);
				}

				res.append("\t@Column(name=\"" + map.getKey() + "\")\n");
				res.append("\tprivate " + fieldType + " " + fieldName + ";\n");
			}
			
			/** 生成对应的setter与getter方法 */
			res.append("\n\t/** setter and getter method */\n");
			for (Map.Entry<String, Integer> map : columns.entrySet()){
				/** 处理数据类型(数据库的数据类型转化成Java相关类型) */
				String fieldType = toJavaType(map.getValue());
				/** 定义属性名： 将数据库表中的列名全部转换成小写字母，作为属性名 */
				String fieldName = map.getKey().toLowerCase();
				/** 将属属中有"_"符号隔开的单词首字母大写  */
				String[] fields = fieldName.split("_");
				fieldName = fields[0];
				for (int i = 1; i < fields.length; i++){
					fieldName += StringUtils.transferUpper(fields[i]);
				}
				/** setter方法 */
				res.append("\tpublic void set" + StringUtils.transferUpper(fieldName) + "(");
				res.append(fieldType + " " + fieldName);
				res.append("){\n");
				res.append("\t\tthis." + fieldName + " = " + fieldName + ";\n");
				res.append("\t}\n");
				/** getter方法 */
				res.append("\tpublic " + fieldType + " get" + StringUtils.transferUpper(fieldName) + "(){\n");
				res.append("\t\treturn this." + fieldName + ";\n");
				res.append("\t}\n");
			}
			res.append("\n}");
			
			/** 将包名替换成相应的文件目录结构字符串 */
			File file = new File(path + "/" + packageName.replaceAll("\\.", "/"));
			/** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
			if (!file.exists()) file.mkdirs();
			/** 在该目录下创建生存相关的DTO Java文件 */
			file = new File(path + "/" +packageName.replaceAll("\\.", "/") + File.separator + className + ".java");
			/** 创建FileWriter输出流，将拼接好的Java源文件输出 */
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			osw.write(res.toString());
			osw.flush();
			osw.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 生成对应的Pojo源文件
	 * 实体类【Spring注解绑定】
	 * @param path 项目存储路径
	 * @param className 类名
	 * @param packageName 生成的包名
	 * @param columns 列的集合
	 */
	public static void generatorPojoSpring(String path, String className,String tableName,
					String packageName, Map<String, Integer> columns) {
		try{

			/** 定义StringBuider来拼接生成类的字符串 */
			StringBuilder res = new StringBuilder();
			res.append("package " + packageName + ";\n\n");

			/** 拼接生成注释字符串 */
			res.append("/**\n");
			res.append(" * " + className + " 实体类\n");
			res.append(" * @date " + sdf.format(Calendar.getInstance().getTime()) + "\n");
			res.append(" * @version 1.0\n");
			res.append(" */\n");
			/**
			 * @Table(name="数据库表")
			 * 拼接@Table，对应数据库表
			 */
			res.append(" @Table(name=\""+tableName+"\")\n");

			/** 拼接生成类的字符串 */
			res.append("public class " + className + " implements java.io.Serializable{\n\n");
			res.append("\tprivate static final long serialVersionUID = "+ random.nextLong()+"L;\n");
			/** 生成DTO类中相关属性 */
			for (Map.Entry<String, Integer> map : columns.entrySet()){
				/** 处理数据类型(数据库的数据类型转化成Java相关类型) */
				String fieldType = toJavaType(map.getValue());
				/** 定义属性名： 将数据库表中的列名全部转换成小写字母，作为属性名 */
				String fieldName = map.getKey().toLowerCase();
				/** 将属属中有"_"符号隔开的单词首字母大写  */
				String[] fields = fieldName.split("_");
				fieldName = fields[0];
				for (int i = 1; i < fields.length; i++){
					fieldName += StringUtils.transferUpper(fields[i]);
				}
				res.append("\tprivate " + fieldType + " " + fieldName + ";\n");
			}

			/** 生成对应的setter与getter方法 */
			res.append("\n\t/** setter and getter method */\n");
			for (Map.Entry<String, Integer> map : columns.entrySet()){
				/** 处理数据类型(数据库的数据类型转化成Java相关类型) */
				String fieldType = toJavaType(map.getValue());
				/** 定义属性名： 将数据库表中的列名全部转换成小写字母，作为属性名 */
				String fieldName = map.getKey().toLowerCase();
				/** 将属属中有"_"符号隔开的单词首字母大写  */
				String[] fields = fieldName.split("_");
				fieldName = fields[0];
				for (int i = 1; i < fields.length; i++){
					fieldName += StringUtils.transferUpper(fields[i]);
				}
				/** setter方法 */
				res.append("\tpublic void set" + StringUtils.transferUpper(fieldName) + "(");
				res.append(fieldType + " " + fieldName);
				res.append("){\n");
				res.append("\t\tthis." + fieldName + " = " + fieldName + ";\n");
				res.append("\t}\n");
				/** getter方法 */
				res.append("\tpublic " + fieldType + " get" + StringUtils.transferUpper(fieldName) + "(){\n");
				res.append("\t\treturn this." + fieldName + ";\n");
				res.append("\t}\n");
			}
			res.append("\n}");

			/** 将包名替换成相应的文件目录结构字符串 */
			File file = new File(path + "/" + packageName.replaceAll("\\.", "/"));
			/** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
			if (!file.exists()) file.mkdirs();
			/** 在该目录下创建生存相关的DTO Java文件 */
			file = new File(path + "/" +packageName.replaceAll("\\.", "/") + File.separator + className + ".java");
			/** 创建FileWriter输出流，将拼接好的Java源文件输出 */
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			osw.write(res.toString());
			osw.flush();
			osw.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 处理数据类型(数据库的数据类型转化成Java相关类型)
	 * @param dataType
	 * @return Java相关类型
	 */
	private static String toJavaType(int dataType){
		switch (dataType){
			case -5:
				return "Long";
			case 4:
				return "Integer";
			case 5:
				return "Short";
			case 8:
				return "Double";
			case 12:
				return "String";
			case 91:
			case 93:
				return "java.util.Date";
		}
		return "String";
	}
}