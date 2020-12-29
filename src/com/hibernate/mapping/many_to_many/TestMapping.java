package com.hibernate.mapping.many_to_many;

import com.hibernate.mapping.many_to_one.Customer;
import com.hibernate.mapping.many_to_one.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

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
        configuration.addClass(Category.class);
        configuration.addClass(Item.class);
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
        Category category1 = new Category();
        category1.setName("衣服");
        Category category2 = new Category();
        category2.setName("服饰");

        Item item1 = new Item();
        item1.setName("上衣。。");
        Item item2 = new Item();
        item2.setName("鞋子。。");

        //设置关联关系
        category1.getItems().add(item1);
        category1.getItems().add(item2);

        category2.getItems().add(item1);
        category2.getItems().add(item2);

        item1.getCategories().add(category1);
        item1.getCategories().add(category2);

        item2.getCategories().add(category1);
        item2.getCategories().add(category2);

        //执行保存操作
        session.save(category1);
        session.save(category2);
        session.save(item1);
        session.save(item2);
    }

    @Test
    public void testGet() {
        Category category = session.get(Category.class,1);
        System.out.println(category.getName());

        /*
        * 懒加载
        * */
        Set<Item> items = category.getItems();
        System.out.println(items.iterator().next());
    }

}
