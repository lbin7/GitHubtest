package com.github.codegen.code;

import com.github.codegen.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 控制器生成器
 *

 */
public class ControllerGenerator {

    /** 日期格式化对象 */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 生成控制器类
     * @param className
     * @param controllerPackage
     */
    public static void generatorController(String path, String className, String controllerPackage,String authorName,String emailName){
        try{
            // 得到基础包名
            String basePackage = controllerPackage.substring(0, controllerPackage.lastIndexOf("."));
            // 得到类名首字母小写
            String lowerClassName = StringUtils.transferLower(className);


            /** ####################### 生成控制器类  ###################### **/
            /** 定义StringBuider来拼接生成控制器类的字符串 */
            StringBuilder res = new StringBuilder();
            res.append("package " + controllerPackage + ";\n\n");
            res.append("import "+ basePackage +".pojo."+ className +";\n");
            res.append("import "+ basePackage +".service."+ className +"Service;\n");
            res.append("import java.util.List;\n");
            res.append("import org.springframework.web.bind.annotation.*;\n");

            /** 拼接生成注释字符串 */
            res.append("/**\n");
            res.append(" * " + className + "Controller 控制器类\n");
            if (authorName!=null&&!authorName.equals("")){
                res.append(" * @author " + authorName + "\n");
            }
            if (emailName!=null&&!emailName.equals("")){
                res.append(" * @email" + emailName + "\n");
            }
            res.append(" * @date " + sdf.format(Calendar.getInstance().getTime()) + "\n");
            res.append(" * @version 1.0\n");
            res.append(" */\n");

            /** 拼接生成控制器类的字符串 */
            res.append("@RestController\n");
            res.append("@RequestMapping(\"/"+ lowerClassName +"\")\n");
            res.append("public class " + className + "Controller {\n\n");
            res.append("\tprivate "+ className +"Service "+ lowerClassName +"Service;\n\n");

            res.append("\t/** 查询全部方法 */\n");
            res.append("\t@GetMapping(\"/findAll\")\n");
            res.append("\tpublic List<"+className+"> findAll() {\n");
            res.append("\t\treturn "+ lowerClassName +"Service.findAll();\n");
            res.append("\t}\n\n");

            res.append("\t/** 多条件分页查询方法 */\n");
            res.append("\t@GetMapping(\"/findByPage\")\n");
            res.append("\tpublic List<"+className+"> findByPage("+ className +" " + lowerClassName + ",\n");
            res.append("\t\t\tInteger page,Integer rows) {\n");
            res.append("\t\ttry {\n");
            res.append("\t\t\treturn "+ lowerClassName +"Service.findByPage("+ lowerClassName +", page, rows);\n");
            res.append("\t\t}catch (Exception ex){\n");
            res.append("\t\t\tex.printStackTrace();\n");
            res.append("\t\t}\n");
            res.append("\t\treturn null;\n");
            res.append("\t}\n\n");

            res.append("\t/** 根据主键id查询方法 */\n");
            res.append("\t@GetMapping(\"/findOne\")\n");
            res.append("\tpublic "+ className +" findOne(Long id) {\n");
            res.append("\t\ttry {\n");
            res.append("\t\t\treturn "+ lowerClassName +"Service.findOne(id);\n");
            res.append("\t\t}catch (Exception ex){\n");
            res.append("\t\t\tex.printStackTrace();\n");
            res.append("\t\t}\n");
            res.append("\t\treturn null;\n");
            res.append("\t}\n\n");

            res.append("\t/** 添加方法 */\n");
            res.append("\t@PostMapping(\"/save\")\n");
            res.append("\tpublic boolean save(@RequestBody "+ className +" "+ lowerClassName +") {\n");
            res.append("\t\ttry {\n");
            res.append("\t\t\t"+ lowerClassName +"Service.save("+ lowerClassName +");\n");
            res.append("\t\t\treturn true;\n");
            res.append("\t\t}catch (Exception ex){\n");
            res.append("\t\t\tex.printStackTrace();\n");
            res.append("\t\t}\n");
            res.append("\t\treturn false;\n");
            res.append("\t}\n\n");

            res.append("\t/** 修改方法 */\n");
            res.append("\t@PostMapping(\"/update\")\n");
            res.append("\tpublic boolean update(@RequestBody "+ className +" "+ lowerClassName +") {\n");
            res.append("\t\ttry {\n");
            res.append("\t\t\t"+ lowerClassName +"Service.update("+ lowerClassName +");\n");
            res.append("\t\t\treturn true;\n");
            res.append("\t\t}catch (Exception ex){\n");
            res.append("\t\t\tex.printStackTrace();\n");
            res.append("\t\t}\n");
            res.append("\t\treturn false;\n");
            res.append("\t}\n\n");

            res.append("\t/** 删除方法 */\n");
            res.append("\t@GetMapping(\"/delete\")\n");
            res.append("\tpublic boolean delete(Long[] ids) {\n");
            res.append("\t\ttry {\n");
            res.append("\t\t\t"+ lowerClassName +"Service.deleteAll(ids);\n");
            res.append("\t\t\treturn true;\n");
            res.append("\t\t}catch (Exception ex){\n");
            res.append("\t\t\tex.printStackTrace();\n");
            res.append("\t\t}\n");
            res.append("\t\treturn false;\n");
            res.append("\t}\n\n");
            res.append("}\n");

            /** 将包名替换成相应的文件目录结构字符串 */
            File file = new File(path + controllerPackage.replaceAll("\\.", "/"));
            /** 判断是否存在该文件目录;如果不存在就创建出相应的文件目录 */
            if (!file.exists()) file.mkdirs();
            /** 在该目录下创建生存相关的DTO Java文件 */
            file = new File(path + controllerPackage.replaceAll("\\.", "/") + File.separator + className + "Controller.java");
            /** 创建FileWriter输出流，将拼接好的Java源文件输出 */
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            osw.write(res.toString());
            osw.flush();
            osw.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}