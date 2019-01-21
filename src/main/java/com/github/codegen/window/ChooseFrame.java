package com.github.codegen.window;

import com.github.codegen.code.CodeGenHelper;
import com.github.codegen.util.DBConnection;
import com.github.codegen.util.FreeMarkerUtils;
import freemarker.template.Template;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 选择构建模型窗口
 *
 */
public class ChooseFrame {

    // 定义窗口的宽度
    private static final int WIN_WIDTH = 430;
    // 定义窗口的高度
    private static final int WIN_HEIGHT = 510;
    // 定义选择构建模版下拉框
    private JComboBox templateComboBox;
    // 定义面板
    private JPanel jPanel;
    // 定义项目名称
    private String projectName;
    // DTO生成对象
    private CodeGenHelper codeGenHelper;
    // 定义所有的模块
    private java.util.List<String> modules = new ArrayList<>();
    //定义@author名称
    private String authorName="";
    //定义@email地址
    private String emailName="";

    /** 显示选择窗口 */
    public void showChooseWindow(String dbType, final String dbName,
                                 final DBConnection dbConnection){
        // 创建代码生成器助手对象
        codeGenHelper = new CodeGenHelper(dbConnection, dbName);
        // 创建窗口
        final JFrame frame = new JFrame("代码【生成器】");
        // 获取电脑屏幕的宽度与高度
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - WIN_WIDTH) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - WIN_HEIGHT) / 2;
        // 设置窗口显示的位置、宽与高
        frame.setBounds(w, h, WIN_WIDTH, WIN_HEIGHT);

        // 设置窗口的图标
        frame.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png"))
                .getImage());

        // 创建面板
        jPanel = new JPanel();

        // 根据数据库名称生成项目名称
        projectName = dbName.replace("_db", "").replace("_DB", "")
                .replace("db","").replace("DB","");

        /** ########## 第一行 ########### */
        // 创建JLabel
        JLabel dbLabel = new JLabel("数据库名称：");
        dbLabel.setPreferredSize(new Dimension(95,30));
        jPanel.add(dbLabel);
        // 创建JLabel
        JLabel dbNameLabel = new JLabel(dbType + "【"+ dbName + "】");
        dbNameLabel.setPreferredSize(new Dimension(280,30));
        jPanel.add(dbNameLabel);

        /** ########## 第二行 ########### */
        // 创建JLabel
        JLabel tableLabel = new JLabel("去除表名前缀：");
        tableLabel.setPreferredSize(new Dimension(95,30));
        jPanel.add(tableLabel);
        // 创建TextField
        final JTextField tableTextField = new JTextField("tb_");
        // 设置高和宽
        tableTextField.setPreferredSize(new Dimension(280,30));
        jPanel.add(tableTextField);


        /** ########## 第三行 ########### */
        // 创建JLabel
        JLabel templateLabel = new JLabel("选择构建模版：");
        templateLabel.setPreferredSize(new Dimension(95,30));
        jPanel.add(templateLabel);

        // 创建ComboBox数据模型
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("实体类");
        model.addElement("实体类【Spring注解绑定】");
        model.addElement("数据访问层【MyBatis + 通用Mapper】");
        model.addElement("业务层【MyBatis + 通用Mapper】");
        model.addElement("控制器层【Spring + SpringMVC】");
        model.addElement("服务提供者【Spring + MyBatis + Dubbo】");
        model.addElement("服务消费者【Spring + SpringMVC + Dubbo】");
        model.addElement("前端MVC【AngularJS】");
        model.addElement("Redis【Spring Data Redis】");
        model.addElement("Solr【Spring Data Solr】");
        model.addElement("ActiveMQ【Spring JMS】");
        templateComboBox = new JComboBox(model);
        // 设置下拉框的高和宽
        templateComboBox.setPreferredSize(new Dimension(280,30));
        jPanel.add(templateComboBox);

        /** ########## 第四行 ########### */
        // 创建JLabel
        final JLabel groupIdLabel = new JLabel("groupId组名：");
        groupIdLabel.setPreferredSize(new Dimension(95,30));
        // 创建TextField
        final JTextField groupIdTextField = new JTextField("com." + projectName);
        // 设置高和宽
        groupIdTextField.setPreferredSize(new Dimension(280,30));
        jPanel.add(groupIdLabel);
        jPanel.add(groupIdTextField);

        /** ########## 第五行 ########### */
        // 创建JLabel
        final JLabel projectLabel = new JLabel("项目名称：");
        projectLabel.setPreferredSize(new Dimension(95,30));
        // 创建TextField
        final JTextField projectTextField = new JTextField(projectName);
        // 设置高和宽
        projectTextField.setPreferredSize(new Dimension(280,30));

        /** ########## 第六行 ########### */
        // 创建JLabel
        final JLabel authorLabel = new JLabel("@author名：");
        authorLabel.setPreferredSize(new Dimension(95,30));
        // 创建TextField
        final JTextField authorTextField = new JTextField(authorName);
        // 设置高和宽
        authorTextField.setPreferredSize(new Dimension(280,30));

        /** ########## 第七行 ########### */
        // 创建JLabel
        final JLabel emailLabel = new JLabel("@email地址：");
        emailLabel.setPreferredSize(new Dimension(95,30));
        // 创建TextField
        final JTextField emailTextField = new JTextField(emailName);
        // 设置高和宽
        emailTextField.setPreferredSize(new Dimension(280,30));

        /** ########## 第八行 ########### */
        // 创建JLabel
        final JLabel moduleLabel = new JLabel("模块名称：");
        moduleLabel.setPreferredSize(new Dimension(95,30));
        // 创建TextField
        final JTextField moduleTextField = new JTextField(projectName + "-pojo");
        // 设置高和宽
        moduleTextField.setPreferredSize(new Dimension(280,30));

        /** ########## 第九行 ########### */
        // 创建JLabel
        final JLabel packageLabel = new JLabel("包名：");
        packageLabel.setPreferredSize(new Dimension(95,30));
        // 创建TextField
        final JTextField packageTextField = new JTextField("com."+ projectName +".pojo");
        // 设置高和宽
        packageTextField.setPreferredSize(new Dimension(280,30));

        /** ########## 第十行 ########### */
        // 创建JLabel
        final JLabel dirLabel = new JLabel("存储路径：");
        dirLabel.setPreferredSize(new Dimension(95,30));
        // 创建JTextField
        final JTextField chooseTextField = new JTextField();
        // 设置高和宽
        chooseTextField.setPreferredSize(new Dimension(250,30));
        // 创建JButton
        final JButton chooseButton = new JButton("...");
        // 设置高和宽
        chooseButton.setPreferredSize(new Dimension(20,30));
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = showFileChooserDialog();
                if (file != null && file.getPath() != null){
                    // 设置文本框中的内容
                    chooseTextField.setText(file.getPath());
                }
            }
        });


        /** ########## 第十一行 ########### */
        // 创建JButton
        final JButton okButton = new JButton("确定");
        // 设置高和宽
        okButton.setPreferredSize(new Dimension(60,30));
        // 创建JButton
        final JButton cancelButton = new JButton("取消");
        // 设置高和宽
        cancelButton.setPreferredSize(new Dimension(60,30));
        // 取消按钮绑定点击事件
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                System.exit(1);
            }
        });

        // 为下拉框绑定事件
        templateComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取构建类型
                String templateType = templateComboBox.getSelectedItem().toString();
                // 获取新的项目名称
                projectName = projectTextField.getText();
                // 判断用户选择的构建类型
                if ("实体类".equals(templateType)){ // 模版类型
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-pojo");
                    packageTextField.setText("com."+ projectName +".pojo");
                }else if("实体类【Spring注解绑定】".equals(templateType)){ // 模版类型
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-pojo");
                    packageTextField.setText("com."+ projectName +".pojo");
                }else if ("数据访问层【MyBatis + 通用Mapper】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-mapper");
                    packageTextField.setText("com."+ projectName +".mapper");
                }else if ("业务层【MyBatis + 通用Mapper】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-service");
                    packageTextField.setText("com."+ projectName +".service");
                }else if ("服务提供者【Spring + MyBatis + Dubbo】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-sellergoods");
                    packageTextField.setText("com."+ projectName +".sellergoods.service");
                }else if ("控制器层【Spring + SpringMVC】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-web");
                    packageTextField.setText("com."+ projectName +".controller");
                } else if ("服务消费者【Spring + SpringMVC + Dubbo】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-manager-web");
                    packageTextField.setText("com."+ projectName +".manager.controller");
                }else if ("前端MVC【AngularJS】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-angularjs");
                    packageTextField.setText("com."+ projectName +".angularjs");
                }else if ("Redis【Spring Data Redis】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-redis");
                    packageTextField.setText("com."+ projectName +".redis");
                }else if ("Solr【Spring Data Solr】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-solr");
                    packageTextField.setText("com."+ projectName +".solr");
                }else if ("ActiveMQ【Spring JMS】".equals(templateType)){
                    projectTextField.setText(projectName);
                    moduleTextField.setText(projectName + "-jms");
                    packageTextField.setText("com."+ projectName +".jms");
                }

            }
        });
        // 确定按钮绑定点击事件
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 获取构建类型
                String templateType = templateComboBox.getSelectedItem().toString();
                // 获取新的项目名称
                String newProjectName = projectTextField.getText();
                // 获取新的模块名称
                String newModuleName = moduleTextField.getText();
                // 获取新的包名
                String newPackageName = packageTextField.getText();
                // 获取组名
                String groupIdName = groupIdTextField.getText();
                // 获取去除的表名前缀
                String tablePrefix = tableTextField.getText();
                // 设置去除的表名前缀
                codeGenHelper.setTablePrefix(tablePrefix);
                // 获取新的项目名称
                String newAuthorName = authorTextField.getText();
                // 获取新的项目名称
                String newEmailName = emailTextField.getText();

                // 获取用户选择的存储路径
                String chooseDir = chooseTextField.getText();
                // 判断有效参数
                if (newProjectName == null || newProjectName.equals("")
                        || groupIdName == null || groupIdName.equals("")
                        || newModuleName == null || newModuleName.equals("")
                        || newPackageName == null || newPackageName.equals("")
                        || chooseDir == null || chooseDir.equals("")){
                    // 弹出提示窗口
                    JOptionPane.showMessageDialog(null, "AI：请填写参数！",
                            "错误",JOptionPane.ERROR_MESSAGE);
                }else {

                    // 添加到集合中
                    if (!modules.contains(newModuleName)) {
                        modules.add(newModuleName);
                    }
                    // 获取模版对象
                    Template template = FreeMarkerUtils.getTemplate("pom.ftl");
                    // 定义数据模型
                    Map<String, Object> dataModel = new HashMap<>();
                    dataModel.put("groupId", groupIdName);
                    dataModel.put("projectName", newProjectName);
                    dataModel.put("modules", modules);

                    // 父级项目pom.xml
                    dataModel.put("packaging", "pom");
                    dataModel.put("jdk", "1.8");

                    // 创建存储路径
                    File projectDir = new File(chooseDir + "/" + newProjectName);
                    if (!projectDir.exists()) {
                        projectDir.mkdirs();
                    }
                    // 生成父级项目pom.xml
                    try {
                        OutputStreamWriter osw = new OutputStreamWriter(new
                                FileOutputStream(projectDir.getPath() + "/pom.xml"), "UTF-8");
                        template.process(dataModel, osw);
                        osw.flush();
                        osw.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // 从数据模型中删除key
                    dataModel.remove("jdk");
                    dataModel.remove("mapper");
                    dataModel.remove("service");
                    dataModel.remove("controller");
                    dataModel.remove("port");
                    dataModel.remove("angularjs");

                    // 添加模块名称
                    dataModel.put("moduleName", newModuleName);
                    // 创建模块路径
                    File moduleDir = new File(projectDir.getPath() + "/" + newModuleName);
                    if (!moduleDir.exists()) {
                        moduleDir.mkdirs();
                    }

                    if ("服务提供者【Spring + MyBatis + Dubbo】".equals(templateType)){
                        try {
                            // 生成模块pom.xml
                            java.util.List<String> childModules = new ArrayList<>();
                            childModules.add(newModuleName + "-interface");
                            childModules.add(newModuleName + "-service");
                            dataModel.put("modules", childModules);
                            dataModel.put("port","9001");
                            OutputStreamWriter osw = new OutputStreamWriter(new
                                    FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                            template.process(dataModel, osw);
                            osw.flush();
                            osw.close();

                            /**######## 生成Dubbo服务提供者工程 ########*/
                            // moduleDir.getPath(): ..\code\newProjectName\newModuleName
                            // ..\code\newProjectName\newModuleName\newModuleName-interface
                            /**### 生成服务接口模块 ###*/
                            // 创建模块名称
                            File serviceInterfaceDir = new File(moduleDir.getPath() + "/" + newModuleName+ "-interface");
                            if (!serviceInterfaceDir.exists()){
                                serviceInterfaceDir.mkdirs();
                            }
                            // 创建Maven项目目录结构
                            createMavenProjectDir(serviceInterfaceDir.getPath());
                            // 创建包名
                            File packageDir = new File(serviceInterfaceDir.getPath() + "/src/main/java/"
                                    + newPackageName.replaceAll("\\.", "/"));
                            if (!packageDir.exists()){
                                packageDir.mkdirs();
                            }
                            // 创建pom.xml
                            // 获取模版对象
                            template = FreeMarkerUtils.getTemplate("pom-interface.ftl");
                            // 定义数据模型
                            Map<String, Object> data = new HashMap<>();
                            data.put("groupId", groupIdName);
                            data.put("parentName", newModuleName);
                            data.put("moduleName", newModuleName + "-interface");
                            data.put("projectName", newProjectName);
                            osw = new OutputStreamWriter(new
                                    FileOutputStream(serviceInterfaceDir.getPath() + "/pom.xml"), "UTF-8");
                            template.process(data, osw);
                            osw.flush();
                            osw.close();


                            // ..\code\newProjectName\newModuleName\newModuleName-service
                            /**### 生成服务实现模块 ###*/
                            // 创建模块名称
                            File serviceImplDir = new File(moduleDir.getPath() + "/" + newModuleName+ "-service");
                            if (!serviceImplDir.exists()){
                                serviceImplDir.mkdirs();
                            }
                            // 创建Maven项目目录结构
                            createMavenProjectDir(serviceImplDir.getPath());
                            // 创建WEB-INF目录
                            File webinfDir = new File(serviceImplDir.getPath() + "/src/main/webapp/WEB-INF/");
                            if (!webinfDir.exists()) {
                                webinfDir.mkdirs();
                            }
                            // 创建包名
                            packageDir = new File(serviceImplDir.getPath() + "/src/main/java/"
                                    + newPackageName.replaceAll("\\.", "/") + "/impl/");
                            if (!packageDir.exists()){
                                packageDir.mkdirs();
                            }
                            // 创建web.xml
                            // 获取模版对象
                            template = FreeMarkerUtils.getTemplate("web-service.ftl");
                            // 定义数据模型
                            data = new HashMap<>();
                            osw = new OutputStreamWriter(new
                                    FileOutputStream(webinfDir.getPath() + "/web.xml"), "UTF-8");
                            template.process(data, osw);
                            osw.flush();
                            osw.close();
                            // 创建pom.xml
                            // 获取模版对象
                            template = FreeMarkerUtils.getTemplate("pom-service.ftl");
                            // 定义数据模型
                            data = new HashMap<>();
                            data.put("groupId", groupIdName);
                            data.put("parentName", newModuleName);
                            data.put("moduleName", newModuleName + "-service");
                            data.put("projectName", newProjectName);
                            osw = new OutputStreamWriter(new
                                    FileOutputStream(serviceImplDir.getPath() + "/pom.xml"), "UTF-8");
                            template.process(data, osw);
                            osw.flush();
                            osw.close();

                            // 创建log4j.properties
                            // 获取模版对象
                            template = FreeMarkerUtils.getTemplate("log4j.ftl");
                            // 定义数据模型
                            data = new HashMap<>();
                            osw = new OutputStreamWriter(new
                                    FileOutputStream(serviceImplDir.getPath() + "/src/main/resources/log4j.properties"), "UTF-8");
                            template.process(data, osw);
                            osw.flush();
                            osw.close();
                            // 创建applicationContext-service.xml
                            // 获取模版对象
                            template = FreeMarkerUtils.getTemplate("dubbo-provider.ftl");
                            // 定义数据模型
                            data = new HashMap<>();
                            data.put("parentName", newModuleName);
                            data.put("packageName", newPackageName);
                            osw = new OutputStreamWriter(new
                                    FileOutputStream(serviceImplDir.getPath() + "/src/main/resources/applicationContext-service.xml"), "UTF-8");
                            template.process(data, osw);
                            osw.flush();
                            osw.close();

                            // 弹出提示窗口
                            JOptionPane.showMessageDialog(null, "AI：生成Dubbo服务提供者成功！",
                                    "提示信息", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            // 弹出提示窗口
                            JOptionPane.showMessageDialog(null, "AI：生成Dubbo服务提供者失败！",
                                    "提示信息", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        // 创建Maven项目目录结构
                        createMavenProjectDir(moduleDir.getPath());

                        // 判断用户选择的构建类型
                        if ("实体类".equals(templateType)) { // 模版类型
                            try {
                                // 模块pom.xml
                                dataModel.put("packaging", "jar");
                                // 生成模块pom.xml
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(dataModel, osw);
                                osw.flush();
                                osw.close();

                                codeGenHelper.generatorCode(moduleDir.getPath(), newPackageName, 1,newAuthorName,newEmailName);
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成实休类成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成实体类失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        }else if ("实体类【Spring注解绑定】".equals(templateType)) { // 模版类型
                            try {
                                // 模块pom.xml
                                dataModel.put("packaging", "jar");
                                // 生成模块pom.xml
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(dataModel, osw);
                                osw.flush();
                                osw.close();

                                codeGenHelper.generatorCode(moduleDir.getPath(), newPackageName, 6,newAuthorName,newEmailName);
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成实体类成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成实体类失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("数据访问层【MyBatis + 通用Mapper】".equals(templateType)) {
                            try {
                                // 模块pom.xml
                                dataModel.put("packaging", "jar");
                                dataModel.put("mapper", true);

                                // 生成模块pom.xml
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(dataModel, osw);
                                osw.flush();
                                osw.close();
                                // 生成props/db.properties
                                File proDir = new File(moduleDir.getPath() + "/src/main/resources/props/");
                                if (!proDir.exists()){
                                    proDir.mkdirs();
                                }
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("db.ftl");
                                // 定义数据模型
                                Map<String, Object> dbModel = new HashMap<>();
                                dbModel.put("host", dbConnection.getHost());
                                dbModel.put("port", dbConnection.getPort());
                                dbModel.put("dbName", dbName);
                                dbModel.put("username", dbConnection.getUser());
                                dbModel.put("password", dbConnection.getPassword());
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/props/db.properties"), "UTF-8");
                                template.process(dbModel, osw);
                                osw.flush();
                                osw.close();

                                // 生成mybatis-config.xml
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("mybatis-config.ftl");
                                // 定义数据模型
                                dbModel = new HashMap<>();
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/mybatis-config.xml"), "UTF-8");
                                template.process(dbModel, osw);
                                osw.flush();
                                osw.close();

                                // 生成applicationContext-mapper.xml
                                template = FreeMarkerUtils.getTemplate("mybatis-spring.ftl");
                                // 定义数据模型
                                dbModel = new HashMap<>();
                                dbModel.put("url","${jdbc.url}");
                                dbModel.put("username","${jdbc.username}");
                                dbModel.put("password","${jdbc.password}");
                                dbModel.put("driver","${jdbc.driver}");
                                dbModel.put("maxActive","${jdbc.maxActive}");
                                dbModel.put("minIdle","${jdbc.minIdle}");
                                dbModel.put("projectName",newProjectName);
                                dbModel.put("packageName",newPackageName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/applicationContext-mapper.xml"), "UTF-8");
                                template.process(dbModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建数据访问层
                                codeGenHelper.generatorCode(moduleDir.getPath(), newPackageName, 2,newAuthorName,newEmailName);
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成数据访问层成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成数据访问层失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("业务层【MyBatis + 通用Mapper】".equals(templateType)) {
                            try {
                                // 模块pom.xml
                                dataModel.put("packaging", "jar");
                                dataModel.put("service", true);
                                // 生成模块pom.xml
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(dataModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建业务层
                                codeGenHelper.generatorCode(moduleDir.getPath(), newPackageName, 3,newAuthorName,newEmailName);
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成业务层成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成业务层失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("控制器层【Spring + SpringMVC】".equals(templateType)) {
                            try {
                                // 模块pom.xml
                                dataModel.put("packaging", "war");
                                dataModel.put("controller", true);
                                dataModel.put("port", "8080");

                                // 生成模块pom.xml
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(dataModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建web.xml
                                File webinfDir = new File(moduleDir.getPath() + "/src/main/webapp/WEB-INF/");
                                if (!webinfDir.exists()) {
                                    webinfDir.mkdirs();
                                }
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("web-springmvc.ftl");
                                // 定义数据模型
                                Map<String, Object> webModel = new HashMap<>();
                                webModel.put("moduleName", newModuleName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/webapp/WEB-INF/web.xml"), "UTF-8");
                                template.process(webModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建xxx-servlet.xml
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("springmvc.ftl");
                                // 定义数据模型
                                Map<String, Object> mvcModel = new HashMap<>();
                                mvcModel.put("packageName", newPackageName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/" + newModuleName + "-servlet.xml"), "UTF-8");
                                template.process(mvcModel, osw);
                                osw.flush();
                                osw.close();


                                // 创建log4j.properties
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("log4j.ftl");
                                // 定义数据模型
                                Map<String, Object> log4jModel = new HashMap<>();
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/log4j.properties"), "UTF-8");
                                template.process(log4jModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建控制器层
                                codeGenHelper.generatorCode(moduleDir.getPath(), newPackageName, 4,newAuthorName,newEmailName);
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成控制器层成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成控制器层失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("服务消费者【Spring + SpringMVC + Dubbo】".equals(templateType)) {
                            try {
                                /**############ 生成Dubbo服务消费者工程 #############*/

                                // 创建WEB-INF目录
                                File webinfDir = new File(moduleDir.getPath() + "/src/main/webapp/WEB-INF/");
                                if (!webinfDir.exists()) {
                                    webinfDir.mkdirs();
                                }
                                // 创建包名
                                File packageDir = new File(moduleDir.getPath() + "/src/main/java/"
                                        + newPackageName.replaceAll("\\.", "/"));
                                if (!packageDir.exists()){
                                    packageDir.mkdirs();
                                }
                                // 创建web.xml
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("web-springmvc.ftl");
                                // 定义数据模型
                                Map<String,Object> data = new HashMap<>();
                                data.put("moduleName", newModuleName);
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(webinfDir.getPath() + "/web.xml"), "UTF-8");
                                template.process(data, osw);
                                osw.flush();
                                osw.close();

                                // 创建pom.xml
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("pom-consumer.ftl");
                                // 定义数据模型
                                data = new HashMap<>();
                                data.put("groupId", groupIdName);
                                data.put("parentName", newProjectName + "-parent");
                                data.put("moduleName", newModuleName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(data, osw);
                                osw.flush();
                                osw.close();

                                // 创建log4j.properties
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("log4j.ftl");
                                // 定义数据模型
                                data = new HashMap<>();
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/log4j.properties"), "UTF-8");
                                template.process(data, osw);
                                osw.flush();
                                osw.close();

                                // 创建springmvc配置文件
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("dubbo-consumer.ftl");
                                // 定义数据模型
                                data = new HashMap<>();
                                data.put("moduleName", newModuleName);
                                data.put("packageName", newPackageName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/"+ newModuleName +"-servlet.xml"), "UTF-8");
                                template.process(data, osw);
                                osw.flush();
                                osw.close();

                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成Dubbo服务消费者成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成Dubbo服务消费者失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("前端MVC【AngularJS】".equals(templateType)) {
                            try {
                                // 模块pom.xml
                                dataModel.put("packaging", "war");
                                dataModel.put("angularjs", true);
                                dataModel.put("port", "8080");
                                // 生成模块pom.xml

                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(dataModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建web.xml
                                File webinfDir = new File(moduleDir.getPath() + "/src/main/webapp/WEB-INF/");
                                if (!webinfDir.exists()) {
                                    webinfDir.mkdirs();
                                }
                                // 创建包名
                                File packageDir = new File(moduleDir.getPath() + "/src/main/java/"
                                        + newPackageName.replaceAll("\\.", "/"));
                                if (!packageDir.exists()){
                                    packageDir.mkdirs();
                                }
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("web-springmvc.ftl");
                                // 定义数据模型
                                Map<String, Object> webModel = new HashMap<>();
                                webModel.put("moduleName", newModuleName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/webapp/WEB-INF/web.xml"), "UTF-8");
                                template.process(webModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建xxx-servlet.xml
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("springmvc.ftl");
                                // 定义数据模型
                                Map<String, Object> mvcModel = new HashMap<>();
                                mvcModel.put("packageName", newPackageName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/" + newModuleName + "-servlet.xml"), "UTF-8");
                                template.process(mvcModel, osw);
                                osw.flush();
                                osw.close();

                                // 创建log4j.properties
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("log4j.ftl");
                                // 定义数据模型
                                Map<String, Object> log4jModel = new HashMap<>();
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/log4j.properties"), "UTF-8");
                                template.process(log4jModel, osw);
                                osw.flush();
                                osw.close();


                                // 创建js目录
                                File jsDir = new File(moduleDir.getPath() + "/src/main/webapp/js/");
                                if (!jsDir.exists()) {
                                    jsDir.mkdirs();
                                }
                                // 创建service目录
                                File serviceDir = new File(moduleDir.getPath() + "/src/main/webapp/js/service/");
                                if (!serviceDir.exists()) {
                                    serviceDir.mkdirs();
                                }
                                // 创建controller目录
                                File controllerDir = new File(moduleDir.getPath() + "/src/main/webapp/js/controller/");
                                if (!controllerDir.exists()) {
                                    controllerDir.mkdirs();
                                }
                                /** ####### 生成基础层js ######## */
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("base.ftl");
                                // 定义数据模型
                                Map<String, Object> jsModel = new HashMap<>();
                                jsModel.put("page", true);
                                jsModel.put("projectName", newProjectName);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(jsDir.getPath() + "/base-pagination.js"), "UTF-8");
                                template.process(jsModel, osw);
                                osw.flush();
                                osw.close();

                                jsModel.put("page", false);
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(jsDir.getPath() + "/base.js"), "UTF-8");
                                template.process(jsModel, osw);
                                osw.flush();
                                osw.close();

                                /** ####### 生成服务层js ######## */
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("baseService.ftl");
                                // 定义数据模型
                                jsModel = new HashMap<>();
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(serviceDir + "/baseService.js"), "UTF-8");
                                template.process(jsModel, osw);
                                osw.flush();
                                osw.close();

                                /** ####### 生成基础控制器js ######## */
                                // 获取模版对象
                                template = FreeMarkerUtils.getTemplate("baseController.ftl");
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(controllerDir + "/baseController.js"), "UTF-8");
                                template.process(jsModel, osw);
                                osw.flush();
                                osw.close();

                                // 生成前端MVC
                                codeGenHelper.generatorCode(moduleDir.getPath(), newProjectName, 5,newAuthorName,newEmailName);

                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成前端MVC成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：生成前端MVC失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("Redis【Spring Data Redis】".equals(templateType)) {
                            try {
                                // 数据模型
                                Map<String,Object> model = new HashMap<>();
                                model.put("parentName", newProjectName);
                                model.put("groupId", groupIdName);
                                model.put("moduleName", newModuleName);
                                // 获取模版对象，生成模块pom.xml
                                template = FreeMarkerUtils.getTemplate("pom-redis.ftl");
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 数据模型
                                model = new HashMap<>();
                                model.put("host", "${redis.host}");
                                model.put("port", "${redis.port}");
                                // 获取模版对象，生成applicationContext-redis.xml
                                template = FreeMarkerUtils.getTemplate("spring-data-redis.ftl");
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/applicationContext-redis.xml"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 数据模型
                                model = new HashMap<>();
                                // 获取模版对象，生成redis.properties
                                template = FreeMarkerUtils.getTemplate("redis.ftl");
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/redis.properties"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：整合Spring Data Redis成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：整合Spring Data Redis失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("Solr【Spring Data Solr】".equals(templateType)) {
                            try {
                                // 数据模型
                                Map<String,Object> model = new HashMap<>();
                                model.put("parentName", newProjectName);
                                model.put("groupId", groupIdName);
                                model.put("moduleName", newModuleName);
                                // 获取模版对象，生成模块pom.xml
                                template = FreeMarkerUtils.getTemplate("pom-solr.ftl");
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 数据模型
                                model = new HashMap<>();
                                // 获取模版对象，生成applicationContext-solr.xml
                                template = FreeMarkerUtils.getTemplate("spring-data-solr.ftl");
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/applicationContext-solr.xml"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：整合Spring Data Solr成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：整合Spring Data Solr失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if ("ActiveMQ【Spring JMS】".equals(templateType)) {
                            try {
                                // 数据模型
                                Map<String,Object> model = new HashMap<>();
                                model.put("parentName", newProjectName);
                                model.put("groupId", groupIdName);
                                model.put("moduleName", newModuleName);
                                // 获取模版对象，生成模块pom.xml
                                template = FreeMarkerUtils.getTemplate("pom-jms.ftl");
                                OutputStreamWriter osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/pom.xml"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 数据模型
                                model = new HashMap<>();
                                // 获取模版对象，生成applicationContext-jms.xml
                                template = FreeMarkerUtils.getTemplate("spring-jms.ftl");
                                osw = new OutputStreamWriter(new
                                        FileOutputStream(moduleDir.getPath() + "/src/main/resources/applicationContext-jms.xml"), "UTF-8");
                                template.process(model, osw);
                                osw.flush();
                                osw.close();

                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：整合Spring JMS成功！",
                                        "提示信息", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                // 弹出提示窗口
                                JOptionPane.showMessageDialog(null, "AI：整合Spring JMS失败！",
                                        "提示信息", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        jPanel.add(projectLabel);
        jPanel.add(projectTextField);
        jPanel.add(authorLabel);
        jPanel.add(authorTextField);
        jPanel.add(emailLabel);
        jPanel.add(emailTextField);
        jPanel.add(moduleLabel);
        jPanel.add(moduleTextField);
        jPanel.add(packageLabel);
        jPanel.add(packageTextField);
        jPanel.add(dirLabel);
        jPanel.add(chooseTextField);
        jPanel.add(chooseButton);
        jPanel.add(okButton);
        jPanel.add(cancelButton);
        // 设置布局(离上边、下边10个像素)
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12,12));

        // 窗口中添加面板
        frame.add(jPanel);
        // 设置窗口默认关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口的背景颜色
        frame.setBackground(Color.WHITE);
        // 设置窗口不能最大化
        frame.setResizable(false);
        // 设置窗口可见
        frame.setVisible(true);
    }

    // 创建Maven项目目录结构
    private void createMavenProjectDir(String path){
        // 创建main
        File mainDir1 = new File(path + "/src/main/java");
        if (!mainDir1.exists()) {
            mainDir1.mkdirs();
        }
        File mainDir2 = new File(path + "/src/main/resources");
        if (!mainDir2.exists()) {
            mainDir2.mkdirs();
        }
        // 创建test
        File testDir1 = new File(path + "/src/test/java");
        if (!testDir1.exists()) {
            testDir1.mkdirs();
        }
        File testDir2 = new File(path + "/src/test/resources");
        if (!testDir2.exists()) {
            testDir2.mkdirs();
        }
    }

    /** 选择文件对话框 */
    private File showFileChooserDialog(){
        // 创建文件选择对话框
        JFileChooser jFileChooser = new JFileChooser();
        // 设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView .getFileSystemView();
        jFileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        // JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        // 弹出的提示框的标题
        jFileChooser.showDialog(new JLabel(), "确定");
        // 用户选择的路径或文件
        File file = jFileChooser.getSelectedFile();
        return file;
    }
}