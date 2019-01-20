package com.github.codegen.code;

import com.github.codegen.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 业务层接口与实现类生成
 * @author LB
 * @date 2019-1-20 下午5:31:17
 * @version 1.0
 */
public class ServiceGenerator {
	
	/** 日期格式化对象 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 生成业务层接口与实现类
	 * @param className
	 * @param servicePackage
	 */
	public static void generatorService(String path, String className, String servicePackage){
		try{
			// 得到基础包名
			String basePackage = servicePackage.substring(0, servicePackage.lastIndexOf("."));
			// 得到类名首字母小写
			String lowerClassName = StringUtils.transferLower(className);
			/** ####################### 生成接口类 ###################### **/
			/** 定义StringBuider来拼接生成接口类的字符串 */
			StringBuilder res = new StringBuilder();
			res.append("package " + servicePackage + ";\n\n");
			res.append("import "+ basePackage +".pojo."+ className +";\n");
			res.append("import java.util.List;\n");
			res.append("import java.io.Serializable;\n");

			/** 拼接生成注释字符串 */
			res.append("/**\n");
			res.append(" * " + className + "Service 服务接口\n");
			res.append(" * @date " + sdf.format(Calendar.getInstance().getTime()) + "\n");
			res.append(" * @version 1.0\n");
			res.append(" */\n");

			/** 拼接生成接口类的字符串 */
			res.append("public interface " + className + "Service {\n\n");
			res.append("\t/** 添加方法 */\n");
			res.append("\tvoid save("+ className +" " + lowerClassName + ");\n\n");
			res.append("\t/** 修改方法 */\n");
			res.append("\tvoid update("+ className +" " + lowerClassName + ");\n\n");
			res.append("\t/** 根据主键id删除 */\n");
			res.append("\tvoid delete(Serializable id);\n\n");
			res.append("\t/** 批量删除 */\n");
			res.append("\tvoid deleteAll(Serializable[] ids);\n\n");
			res.append("\t/** 根据主键id查询 */\n");
			res.append("\t"+ className +" findOne(Serializable id);\n\n");
			res.append("\t/** 查询全部 */\n");
			res.append("\tList<"+ className +"> findAll();\n\n");
			res.append("\t/** 多条件分页查询 */\n");
			res.append("\tList<"+ className +"> findByPage("+ className +" "+ lowerClassName +", int page, int rows);");
			res.append("\n\n}");
			
			/** 将包名替换成相应的文件目录结构字符串 */
			File file = new File(path + servicePackage.replaceAll("\\.", "/"));
			/** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
			if (!file.exists()) file.mkdirs();
			/** 在该目录下创建生存相关的DTO Java文件 */
			file = new File(path + servicePackage.replaceAll("\\.", "/") + File.separator + className + "Service.java");
			/** 创建FileWriter输出流，将拼接好的Java源文件输出 */
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			osw.write(res.toString());
			osw.flush();
			osw.close();


			/** ####################### 生成接口实现类  ###################### **/
			/** 定义StringBuider来拼接生成实现类的字符串 */
			res = new StringBuilder();
			res.append("package " + servicePackage + ".impl;\n\n");
			res.append("import "+ basePackage +".pojo."+ className +";\n");
			res.append("import "+ basePackage +".mapper."+ className +"Mapper;\n");
			res.append("import "+ servicePackage +"."+ className +"Service;\n");
			res.append("import java.util.List;\n");
			res.append("import com.github.pagehelper.ISelect;\n");
			res.append("import com.github.pagehelper.PageHelper;\n");
			res.append("import com.github.pagehelper.PageInfo;\n");
			res.append("import org.springframework.beans.factory.annotation.Autowired;\n");
			res.append("import tk.mybatis.mapper.entity.Example;\n");
			res.append("import java.io.Serializable;\n");
			res.append("import java.util.Arrays;\n");


			/** 拼接生成注释字符串 */
			res.append("/**\n");
			res.append(" * " + className + "ServiceImpl 服务接口实现类\n");
			res.append(" * @date " + sdf.format(Calendar.getInstance().getTime()) + "\n");
			res.append(" * @version 1.0\n");
			res.append(" */\n");

			/** 拼接生成接口实现类的字符串 */
			res.append("public class " + className + "ServiceImpl implements "+ className +"Service {\n\n");
			res.append("\t@Autowired\n");
			res.append("\tprivate "+ className +"Mapper "+lowerClassName+"Mapper;\n\n");

			res.append("\t/** 添加方法 */\n");
			res.append("\tpublic void save("+ className +" " + lowerClassName + "){\n");
			res.append("\t\ttry {\n");
			res.append("\t\t\t"+ lowerClassName +"Mapper.insertSelective("+ lowerClassName +");\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}\n\n");

			res.append("\t/** 修改方法 */\n");
			res.append("\tpublic void update("+ className +" " + lowerClassName + "){\n");
			res.append("\t\ttry {\n");
			res.append("\t\t\t"+ lowerClassName +"Mapper.updateByPrimaryKeySelective("+ lowerClassName +");\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}\n\n");

			res.append("\t/** 根据主键id删除 */\n");
			res.append("\tpublic void delete(Serializable id){\n");
			res.append("\t\ttry {\n");
			res.append("\t\t\t"+ lowerClassName +"Mapper.deleteByPrimaryKey(id);\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}\n\n");

			res.append("\t/** 批量删除 */\n");
			res.append("\tpublic void deleteAll(Serializable[] ids){\n");
			res.append("\t\ttry {\n");
			res.append("\t\t\t// 创建示范对象\n");
			res.append("\t\t\tExample example = new Example("+ className +".class);\n");
			res.append("\t\t\t// 创建条件对象\n");
			res.append("\t\t\tExample.Criteria criteria = example.createCriteria();\n");
			res.append("\t\t\t// 创建In条件\n");
			res.append("\t\t\tcriteria.andIn(\"id\", Arrays.asList(ids));\n");
			res.append("\t\t\t// 根据示范对象删除\n");
			res.append("\t\t\t"+ lowerClassName +"Mapper.deleteByExample(example);\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}\n\n");

			res.append("\t/** 根据主键id查询 */\n");
			res.append("\tpublic "+ className +" findOne(Serializable id){\n");
			res.append("\t\ttry {\n");
			res.append("\t\t\treturn "+ lowerClassName +"Mapper.selectByPrimaryKey(id);\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}\n\n");

			res.append("\t/** 查询全部 */\n");
			res.append("\tpublic List<"+ className +"> findAll(){\n");
			res.append("\t\ttry {\n");
			res.append("\t\t\treturn "+ lowerClassName +"Mapper.selectAll();\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}\n\n");

			res.append("\t/** 多条件分页查询 */\n");
			res.append("\tpublic List<"+ className +"> findByPage("+ className +" " + lowerClassName +", int page, int rows){\n");
			res.append("\t\ttry {\n");

			res.append("\t\t\tPageInfo<"+ className +"> pageInfo = PageHelper.startPage(page, rows)\n");
			res.append("\t\t\t\t.doSelectPageInfo(new ISelect() {\n");
			res.append("\t\t\t\t\t@Override\n");
			res.append("\t\t\t\t\tpublic void doSelect() {\n");
			res.append("\t\t\t\t\t\t"+lowerClassName+"Mapper.selectAll();\n");
			res.append("\t\t\t\t\t}\n");
			res.append("\t\t\t\t});\n");
			res.append("\t\t\treturn pageInfo.getList();\n");
			res.append("\t\t}catch (Exception ex){\n");
			res.append("\t\t\tthrow new RuntimeException(ex);\n");
			res.append("\t\t}\n");
			res.append("\t}");
			res.append("\n\n}");

			/** 将包名替换成相应的文件目录结构字符串 */
			file = new File(path + servicePackage.replaceAll("\\.", "/") + "/impl/");
			/** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
			if (!file.exists()) file.mkdirs();
			/** 在该目录下创建生存相关的DTO Java文件 */
			file = new File(path + servicePackage.replaceAll("\\.", "/") + "/impl/" + File.separator + className + "ServiceImpl.java");
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