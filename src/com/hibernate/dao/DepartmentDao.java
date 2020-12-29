package com.hibernate.dao;

import com.hibernate.hibernateutils.HibernateUtils;
import com.hibernate.hql.Department;
import com.hibernate.hql.Employee;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * @author lijichen
 * @date 2020/11/12 - 15:16
 */
public class DepartmentDao {
    public void save(Department dept) {
        //内部获取session对象
        //获取和当前线程绑定的session对象
        //1,不需要从外部传入session对象
        //2,多个DAO方法可以使用同一个事物
        Session session = HibernateUtils.getInstance().getSession();

        System.out.println(session.hashCode());

        session.save(dept);
    }
}
