<?xml version="1.0" encoding="utf-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jms="http://www.springframework.org/schema/jms"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                  http://www.springframework.org/schema/beans/spring-beans.xsd
                  http://www.springframework.org/schema/jms
                  http://www.springframework.org/schema/jms/spring-jms.xsd">

    <!--########### 通用配置 #############-->
    <bean id="activeMQConnectionFactory"
          class="org.apache.activemq.ActiveMQConnectionFactory">
        <!-- 设置brokerURL(连接消息中间件的地址) -->
        <property name="brokerURL" value="tcp://192.168.12.131:61616"/>
    </bean>
    <!-- 配置spring-jms的单例连接工厂 -->
    <bean id="connectionFactory"
          class="org.springframework.jms.connection.SingleConnectionFactory">
        <!-- 设置ActiveMQ的连接工厂交由它管理-->
        <property name="targetConnectionFactory" ref="activeMQConnectionFactory"/>
    </bean>

    <!--########### 消息生产者配置 #############-->
    <!-- 配置JmsTemplate模版对象发送消息 -->
    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <!-- 设置连接工厂 -->
        <property name="connectionFactory" ref="connectionFactory"/>
        <!-- 设置默认的目标 -->
        <property name="defaultDestination" ref="queue"/>
    </bean>
    <!-- 配置消息的目标(点对点) -->
    <bean id="queue" class="org.apache.activemq.command.ActiveMQQueue">
        <!-- 设置队列名称 -->
        <constructor-arg value="spring-queue"/>
    </bean>

    <!--########### 消息消费者配置 #############-->
    <!--
        配置监听器容器
        connection-factory: 连接工厂
        destination-type: 目标类型
    -->
    <jms:listener-container connection-factory="connectionFactory"
                            destination-type="queue">
        <!-- 配置监听器 destination: 队列名称 ref: 引用消息监听器Bean -->
        <jms:listener destination="spring-queue" ref="queueMessageListener"/>
    </jms:listener-container>
    <!-- 配置自定义的消息监听器 -->
    <bean id="queueMessageListener"
          class="com.cct.activemq.spring.listener.QueueMessageListener"/>
</beans>