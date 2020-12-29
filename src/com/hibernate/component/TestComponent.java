package com.hibernate.component;

import com.hibernate.helloworld2.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lijichen
 * @date 2020/11/8 - 17:09
 */
public class TestComponent {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {

        Configuration configuration = new Configuration().configure();
        configuration.addClass(Worker.class);
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
        Worker worker = new Worker("123",new Pay(123,new Worker()));
        session.save(worker);


    }
}
