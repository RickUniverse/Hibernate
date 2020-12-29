package com.hibernate.helloworld2;

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

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * @author lijichen
 * @date 2020/11/7 - 15:39
 */
public class TestNews {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {

        Configuration configuration = new Configuration().configure();
        configuration.addClass(News.class);
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
        session.save(new News(0,"ter","sd","dfd",new Date(new java.util.Date().getTime())));
    }

    @Test
    public void testFlush() {
        News news = session.get(News.class, 1);
        news.setAuthor("张三1");
        System.out.println(news);
        /*
        * 发送修改请求给数据库
        * */
        session.flush();
        News news1 = session.get(News.class, 1);
        System.out.println(news1);
    }

    @Test
    public void testRefresh() {
        News news = session.get(News.class, 1);

        System.out.println(news);
        /*
        * 强制发送select请求，保证数据跟数据库中的数据一致
        * */
        session.refresh(news);
        System.out.println(news);

    }



    @Test
    public void testSave() {
        News news = new News();
        news.setTitle("titles");
        news.setAuthor("qwe");
        news.setDate(new Date(new java.util.Date().getTime()));

        System.out.println(news);

        session.save(news);

        System.out.println(news);
    }

    @Test
    public void testPersist() {
        News news = new News();
        news.setId(123);
        news.setTitle("titles");
        news.setAuthor("qwe");
        news.setDate(new Date(new java.util.Date().getTime()));

        System.out.println(news);

        session.persist(news);
    }


    @Test
    public void testGet() {
        News news = session.get(News.class, 1);
        System.out.println(news.getClass().getName());
//        session.close();
//        System.out.println(news);
    }
    /*
    * 懒加载
    * */
    @Test
    public void testLoad() {
        News news = session.load(News.class, 1);
//        session.close();
        System.out.println(news.getClass().getName());
//        System.out.println(news);
    }


    @Test
    public void testSaveOrUpdate() {

        News news = new News();
        news.setTitle("titles");
        news.setAuthor("qwe");
        news.setDate(new Date(new java.util.Date().getTime()));

        /*执行保存操作*/
        session.saveOrUpdate(news);

        news.setTitle("sssssss");
        /*执行修改操作*/
        session.saveOrUpdate(news);

    }
    @Test
    public void testUpdate() {
        News news = session.get(News.class, 1);

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

//        news.setTitle("11111");
        //一个session中不能有两个相同oid的持久化对象
//        session.get(News.class,1);
        session.update(news);
    }

    @Test
    public void testDelete() {
        News news = new News();
        news.setId(3);

        //游离对象删除
        session.delete(news);

        System.out.println(news);
        News news1 = session.get(News.class, 4);
        //持久化对象删除
        session.delete(news1);

        /*
        * cfg.xml 配置hibernate.use_identifier_rollback
        * 删除后id值置空
        * */
        System.out.println(news1);

    }

    @Test
    public void testEvict() {
        News news = session.get(News.class, 4);
        news.setTitle("asdg");
        News news2 = session.get(News.class, 5);
        news2.setTitle("asdg");

        //从session中移除id为4的对象
        session.evict(news);
    }



    /*
    * 调用存储过程
    * */
    @Test
    public void testWork() {

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);

                //调用存储过程代码....
            }
        });
    }


    @Test
    public void testDynamic() {
        News news = session.get(News.class, 4);
        news.setTitle("123");
    }

    @Test
    public void testFormula() {
        News news = session.get(News.class, 4);
        System.out.println(news.getDesc());
    }
}
