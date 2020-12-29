package com.hibernate.strategy;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Hibernate;
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
public class TestStrategy {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {

        Configuration configuration = new Configuration().configure();
        /*
        * 如果是多对一则需要将两个类都添加进去然后注册
        * */
        configuration.addClass(Customer.class);
        configuration.addClass(Order.class);
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
    public void testClassStrategy() {
        Customer customer = session.load(Customer.class,1);
        System.out.println(customer.getClass().getName());
        System.out.println(customer.getId());

        System.out.println(customer.getName());

    }

    @Test
    public void testSetStrategy() {
        Customer customer = session.get(Customer.class,1);

        System.out.println(customer.getOrders().size());
        Order order = new Order();
        order.setId(1);
        /*
        * 会发送查询是否包含的语句
        * 增强型延迟检索，会尽可能延迟初始化order项
        * */
        System.out.println(customer.getOrders().contains(order));

        //静态方法显示初始化项
        Hibernate.initialize(customer.getOrders());

    }


    @Test
    public void testMany_to_one() {
        List<Order> orders = session.createQuery("FROM Order").list();

        for (Order order : orders) {
            if (order.getCustomer()!= null)
                System.out.println(order.getCustomer().getName());

        }
    }
    @Test
    public void testBathSize2() {
        Customer customer = session.get(Customer.class,1);

        System.out.println(customer.getOrders().size());
    }
    @Test
    public void testBathSize() {

        List<Customer> customers = session.createQuery("from Customer").list();

        System.out.println(customers.size());

        for (Customer customer : customers) {
            if (customer.getOrders().size() != 0) {
                System.out.println(customer.getOrders().size());
            }
        }

    }

}
