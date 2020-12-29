package com.hibernate.hibernateutils;

import com.hibernate.hql.Department;
import com.hibernate.hql.Employee;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * 单例模式
 * @author lijichen
 * @date 2020/11/12 - 15:18
 */
public class HibernateUtils {

    public HibernateUtils() {}

    private static HibernateUtils instance = new HibernateUtils();

    public static HibernateUtils getInstance() {
        return instance;
    }

    private SessionFactory sessionFactory;

    /**
     *
     * @return
     */
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            /*
             * 如果是多对一则需要将两个类都添加进去然后注册
             * */
            configuration.addClass(Department.class);
            configuration.addClass(Employee.class);
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            //注册
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }


    /**
     * 获取当前线程绑定的session
     * 使用.getCurrentSession()方法
     * @return
     */
    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
}
