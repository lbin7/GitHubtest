package com.github.codegen.util;


import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * FreeMarker模版工具类
 *
 * @author LB
 * @date 2019-1-20 下午5:31:17
 * @version 1.0
 */
public class FreeMarkerUtils {
    /** 定义模版配置信息对象 */
    private static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(FreeMarkerUtils.class, "/ftl/");
    }
    /** 获取模版对象 */
    public static Template getTemplate(String ftlFileName){
        try {
            return configuration.getTemplate(ftlFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}