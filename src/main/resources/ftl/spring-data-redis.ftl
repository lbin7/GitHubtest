<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- ################# 配置Redis单机版 ############### -->
    <!-- 加载配置文件 -->
    <context:property-placeholder location="classpath:redis.properties"/>

    <!-- 配置连接工厂 -->
    <bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <!-- 设置host -->
        <property name="hostName" value="${host}"/>
        <!-- 设置port -->
        <property name="port" value="${port}"/>
    </bean>


    <!-- ################# 配置Redis集群版(分布式缓存) ############### -->
    <!-- 配置资源属性源 -->
    <bean id="propertySource" class="org.springframework.core.io.support.ResourcePropertySource">
        <constructor-arg name="resource" value="classpath:redis.properties"/>
    </bean>
    <!-- 配置Redis集群节点信息 -->
    <bean id="clusterConfig" class="org.springframework.data.redis.connection.RedisClusterConfiguration">
        <!-- 配置属性源(指定节点信息) -->
        <constructor-arg name="propertySource" ref="propertySource"/>
    </bean>
    <!-- 配置连接工厂 -->
    <!--<bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <constructor-arg name="clusterConfig" ref="clusterConfig"/>
    </bean>-->

    <!-- 配置RedisTemplate操作Redis -->
    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
        <!-- 设置连接工厂 -->
        <property name="connectionFactory" ref="connectionFactory"/>
    </bean>
</beans>