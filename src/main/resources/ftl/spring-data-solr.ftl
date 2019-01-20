<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:solr="http://www.springframework.org/schema/data/solr"
       xsi:schemaLocation="http://www.springframework.org/schema/data/solr
       http://www.springframework.org/schema/data/solr/spring-solr.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--############## 配置Solr单机版 ##############-->
    <!-- 配置SolrServer -->
    <!--<solr:solr-server id="solrServer"
                      url="http://192.168.12.131:8088/solr/collection1"/>-->


    <!--############## 配置Solr集群版 ##############-->
    <bean id="solrServer" class="org.apache.solr.client.solrj.impl.CloudSolrServer">
        <!-- 设置zookeeper的主机与端口 -->
        <constructor-arg name="zkHost" value="192.168.12.131:3181,192.168.12.131:3182,192.168.12.131:3183"/>
        <!-- 设置默认的索引库集合 -->
        <property name="defaultCollection" value="pyg_collection"/>
    </bean>

    <!-- 配置SolrTemplate，它可以对Solr索引库做CRUD操作 -->
    <bean id="solrTemplate" class="org.springframework.data.solr.core.SolrTemplate">
        <constructor-arg name="solrServer" ref="solrServer"/>
    </bean>
</beans>