package com.hibernate.mapping.many_to_one_to_many;

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
    public void test() {
        Customer customer = new Customer("AA");

        /*构造参数是为了传参数*/
        Order order1 = new Order(customer);
        Order order2 = new Order(customer);

        /*一对多关联关系*/
        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        /*只有insert 语句*/
        session.save(customer);

//        session.save(order1);
//        session.save(order2);
        /*
        * 如果先保存 n 的这一端，在保存 1 的这一端 ，会发送五条语句，最后两条修改语句，
        * 会在transaction. commit();事务提交是更改 n 这一端关联的 1 这一端的id
        * */
//        session.save(order1);
//        session.save(order2);
//
//        session.save(customer);
    }


    @Test
    public void testGet() {
        Customer customer = session.get(Customer.class,3);

        /*
        * 延迟加载
        * */
        System.out.println(customer.getOrders().getClass().getName());
        Order next = customer.getOrders().iterator().next();
        long size = customer.getOrders().size();
        System.out.println(customer.getOrders());
    }
    @Test
    public void testUpdate() {
        Order order = session.get(Order.class,1);
        order.getCustomer().getOrders();
    }
    @Test
    public void testDelete() {
        /*
        * 在不确定层级关系的情况下不能直接删除Customer
        * */
        Customer customer = session.get(Customer.class,1);
//        session.delete(customer);
        customer.getOrders().clear();

//        Order order = session.get(Order.class,2);
//        session.delete(order);
    }
}
