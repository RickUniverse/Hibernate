package com.hibernate.helloworld;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.sql.Date;

/**
 * @author lijichen
 * @date 2020/11/6 - 18:09
 */
public class TestHibernate {
    @Test
    public void test() {
        //1,创建一个SessionFactory对象
        SessionFactory sessionFactory = null;
        //1>,创建Configuration 对象：对应hibernate 的基本配置信息和对象关系映射
        Configuration configure = new Configuration().configure();
        /*
        * 出现Unknown entity: com.hibernate.helloworld.NewsEntity错误加上
        * */
        configure.addClass(NewsEntity.class);


        //2>,创建一个ServletRegistry 对象
        //hibernate 的任何配置和服务都需要在该对象中注册才能有效
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configure.getProperties())
                                                                             .build();
        //3>,
        sessionFactory = configure.buildSessionFactory(serviceRegistry);

        //2.创建一个session对象
        Session session = sessionFactory.openSession();
        //3.开启事务
        Transaction transaction = session.beginTransaction();
        //4，执行保存操作
//        NewsEntity news = new NewsEntity("title","author",new Date(new java.util.Date().getTime()));
//        session.save(news);

        NewsEntity newsEntity = session.get(NewsEntity.class, 2);
        System.out.println(newsEntity);

        //5,提交事务
        transaction.commit();
        //6,关闭session
        session.close();
        //7,关闭sessionFactory
        sessionFactory.close();
    }
}
