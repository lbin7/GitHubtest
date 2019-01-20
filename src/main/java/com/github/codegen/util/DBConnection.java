package com.github.codegen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据连接类
 * @author LB
 * @date 2019-1-20 下午5:31:17
 * @version 1.0
 */
public class DBConnection {

    private String driverClass;
    private String jdbcUrlPrefix;
    private String user;
    private String host;
    private String port;
    private String password;
    private static String dbSql = "SELECT TABLE_SCHEMA FROM `TABLES` GROUP BY TABLE_SCHEMA";

    /** 构造器 */
    public DBConnection(String dbType, String host, String username,
                        String password, String port){
        // 判断数据库类型
        if ("mysql".equalsIgnoreCase(dbType)){ // mysql
            this.driverClass = "com.mysql.jdbc.Driver";
            this.jdbcUrlPrefix="jdbc:mysql://"+ host +":" + port ;
        }else{ // oracle
        }
        this.host = host;
        this.port = port;
        this.user = username;
        this.password = password;
    }

    /** 获取MySQL中所有的数据库的名称 */
    public List<String> getSysDataBaseName(){
        try {
            /** 加载驱动类 */
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(jdbcUrlPrefix
                    + "/information_schema", user, password);
            PreparedStatement pstmt = connection.prepareStatement(dbSql);
            ResultSet rs = pstmt.executeQuery();
            // 保存所有的数据库名称
            List<String> dbList = new ArrayList<>();
            while (rs.next()){
                dbList.add(rs.getString(1));
            }
            // 关闭数据连接对象
            connection.close();
            return dbList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** 获取数据库的连接对象 */
    public Connection getConnection(String dbName){
        try {
            /** 加载驱动类 */
            Class.forName(driverClass);
            return DriverManager.getConnection(jdbcUrlPrefix
                    + "/" + dbName, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}