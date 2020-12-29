package com.hibernate.mapping.one_to_one;

import com.hibernate.mapping.many_to_one_to_many.Customer;
import com.hibernate.mapping.many_to_one_to_many.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        configuration.addClass(Department.class);
        configuration.addClass(Manager.class);
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

        Manager manager = new Manager();
        manager.setName("manager1");

        Department department = new Department();
        department.setName("department1");

        //设置关联
        manager.setDepartment(department);
        department.setManager(manager);

        //先保存没有外键列的那个效率高
        session.save(manager);
        session.save(department);

    }

    @Test
    public void testGet() {
        Department department = session.get(Department.class,1);
        System.out.println(department.getManager().getClass());
//        session.close();
        /*
        * 同样是懒加载
        * */
        System.out.println(department.getManager());

//        Manager manager = session.get(Manager.class,1);
//        System.out.println(manager.getDepartment().getClass());
//        System.out.println(manager.getDepartment());

    }
}
