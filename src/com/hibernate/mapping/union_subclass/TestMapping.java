package com.hibernate.mapping.union_subclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author lijichen
 * @date 2020/11/8 - 18:48
 */
public class TestMapping {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {

        Configuration configuration = new Configuration().configure();
        /*
        * 如果是多对一则需要将两个类都添加进去然后注册
        * */
        configuration.addClass(Person.class);
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        //注册
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destory() {
        transaction. commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testSave() {

        Person person = new Person();
        person.setAge(123);
        person.setName("人類");

        session.save(person);


        Student student = new Student();
        student.setSchool("河北大学");
        student.setAge(1233);
        student.setName("张三");

        session.save(student);
    }

    /*
    * 优点
    *   不需要使用辨别者列
    *   子类独有字段能添加 非空约束
    *
    * 缺点
    *   更新操作时效率低
    *   字段冗餘
    *
    * */
    /*
    * 查询父表记录，需把父表和子表汇总到一起查，效率差
    *   子表记录，也只需要查询一张表
    * */
    @Test
    public void testQuery() {
        List<Person> from_person = session.createQuery("from Person").list();
        System.out.println(from_person);

        List<Person> from_student = session.createQuery("from Student").list();
        System.out.println(from_student);
    }

}
