package com.hibernate.hql;

import com.hibernate.dao.DepartmentDao;
import com.hibernate.hibernateutils.HibernateUtils;
import com.hibernate.mapping.joined_subclass.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.jdbc.Work;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author lijichen
 * @date 2020/11/8 - 18:48
 */
public class TesHQL {
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
        configuration.addClass(Employee.class);
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
    public void testHQL() {
        //基于位置的参数
        //1，创建query对象
        String hql = "FROM Employee e WHERE e.salary > ?1 AND e.email LIKE ?2";
        Query<Employee> query = session.createQuery(hql);

        //2，绑定参数
        query.setParameter(1,6000f)
                .setParameter(2,"%%");

        //3.执行查询
        List<Employee> employees = query.list();
        System.out.println(employees.size());

    }
    @Test
    public void testHQLNamedParameter() {
        //基于命名参数
        //1，创建query对象
        String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
        Query<Employee> query = session.createQuery(hql);

        //2，绑定参数
        //支持方法链的编程风格
        query.setParameter("sal",5000f)
                .setParameter("email","%A%");

        //3.执行查询
        List<Employee> employees = query.list();
        System.out.println(employees.size());

    }

    @Test
    public void testQueryPage() {
        /*
        * 分页查询
        * */
        String hql = "FROM Employee";
        Query<Employee> query = session.createQuery(hql);

        int pageNo = 3;
        int pageSize = 5;

        List<Employee> employees = query.setFirstResult((pageNo - 1) * pageSize)
                                        .setMaxResults(pageSize)
                                        .list();
        for (Employee employee : employees) {
            System.out.println(employee.getId());
        }
    }


    /*
    * 命名查询
    * */
    @Test
    public void testNamedQuery() {
        Query query = session.getNamedQuery("selectEmpBySalary");
        List<Employee> list = query.setParameter("minSal", 4000f)
                .setParameter("maxSal", 10000f)
                .list();
        System.out.println(list.size());
    }

    /*
    * 投影查询
    * */
    @Test
    public void testFieldQuery() {
        String hql = "SELECT e.email, e.salary, e.dept FROM Employee e" +
                " WHERE e.dept = :dept";

        Query<Object[]> query = session.createQuery(hql);

        Department dept = new Department();
        dept.setId(80);
        List<Object[]> objects = query.setParameter("dept", dept).list();

        for (Object[] obj : objects) {
            System.out.println(Arrays.asList(obj));
        }
    }
    /*
    * 投影查询进阶
    * */
    @Test
    public void testFieldQuery2() {
        String hql = "SELECT new Employee(e.email, e.salary, e.dept) FROM Employee e" +
                " WHERE e.dept = :dept";

        Query<Employee> query = session.createQuery(hql);

        Department dept = new Department();
        dept.setId(80);
        List<Employee> objects = query.setParameter("dept", dept).list();

        for (Employee emp : objects) {
            System.out.println(emp.getId()+","+emp.getSalary()+","+emp.getDept());
        }
    }


    //报表查询
    @Test
    public void testGroup() {
        String hql = "SELECT min(e.salary), max(e.salary) FROM Employee e" +
                " GROUP BY e.dept" +
                " HAVING min(e.salary) > :minSaly";

        Query query = session.createQuery(hql);
        List<Object[]> objects = query.setParameter("minSaly", 1000f).list();
        for (Object[] object : objects) {
            System.out.println(Arrays.asList(object));

        }

    }

    /*
    * 迫切左外连接
    * */
    @Test
    public void testLeftJoinFetch() {
//        String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.emps";
        String hql = "FROM Department d LEFT JOIN FETCH d.emps";
        Query<Department> query = session.createQuery(hql);

        List<Department> lists = query.list();
        //取出重复元素
        List<Department> departments = new ArrayList<>(new HashSet<>(lists));
        for (Department list : departments) {
            System.out.println(list.getEmps());

        }
    }


    @Test
    public void testLeftJoin() {
//        String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
        /*
        * 内连接，不返回没有员工的部门
        * */
        String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
        Query query = session.createQuery(hql);
        List<Department> list = query.list();

        for (Department department : list) {
            /*
            * 懒加载，每个department都会发送一次请求
            * */
            System.out.println(department.getEmps());

        }


        /*List<Object[]> list = query.list();
        System.out.println(list.size());
        for (Object[] objects : list) {
            System.out.println(Arrays.asList(objects));

        }*/
    }



    /////////////////////////////////////////////////

    @Test
    public void testQBC() {
        //1，创建一个
        Criteria criteria = session.createCriteria(Employee.class);

        //2.添加查询操作
        criteria.add(Restrictions.eq("email","SKUMAR"));
        criteria.add(Restrictions.gt("salary",5000f));

        //3.执行查询
        Employee employee = (Employee) criteria.uniqueResult();
        System.out.println(employee);
    }


    @Test
    public void testQBC2() {
        Criteria criteria = session.createCriteria(Employee.class);

        //1.AND 使用Conjunction表示
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("name","a", MatchMode.ANYWHERE));
        Department department = new Department();
        department.setId(80);
        conjunction.add(Restrictions.eq("dept",department));
        System.out.println(conjunction);

        //2.OR 使用Disjunction
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ge("salary",6000f));
        disjunction.add(Restrictions.isNull("email"));

        criteria.add(conjunction);
        criteria.add(disjunction);

        List list = criteria.list();
        System.out.println(list);

    }


    @Test
    public void testQBC3() {
        //统计查询
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.setProjection(Projections.max("salary"));
        System.out.println(criteria.uniqueResult());
    }

    @Test
    public void testQBC4() {
        //排序和分页
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.addOrder(Order.asc("salary"));
        criteria.addOrder(Order.desc("name"));

        int pageNo = 2;
        int pageSize = 4;
        List list = criteria.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        System.out.println(list);
    }


    @Test
    public void testQBC5() {
        //使用本地sql, 增删改都可以
        String hql = "INSERT INTO DEPARTMENT_TABLE VALUES(?1,?2)";
        NativeQuery sqlQuery = session.createSQLQuery(hql);
        sqlQuery.setParameter(1,280)
                .setParameter(2,"atguigu")
                .executeUpdate();
    }

    @Test
    public void testTwoLevelCache() {
        Employee employee = session.get(Employee.class, 100);
        System.out.println(employee);

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Employee employee1 = session.get(Employee.class, 100);
        System.out.println(employee1);

    }


    //集合级别的二级缓存
    @Test
    public void testTwoLevelCacheList() {
        Department department = session.get(Department.class,10);
        System.out.println(department.getName());
        System.out.println(department.getEmps().size());

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Department department2 = session.get(Department.class,10);
        System.out.println(department2.getName());
        System.out.println(department2.getEmps().size());
    }






    //测试c3p0
    @Test
    public void testWork() {
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);

                //使用原生sql 批量插入速度最快
            }
        });
    }

    /*
    * 查询缓存:依赖于二级缓存
    * */
    @Test
    public void testQueryCache() {
        Query<Department> query = session.createQuery("FROM Department");
        /*
        * 使用查询缓存
        * */
        query.setCacheable(true);
        List<Department> list = query.list();
        System.out.println(list.size());

        List<Department> list2 = query.list();
        System.out.println(list2.size());
    }


    @Test
    public void testIterate() {
        Department department = session.get(Department.class,80);
        /*
        * 下面这一行执行完成之后，二级缓存中就有了部门号为80的Employee对象，
        * */
        System.out.println(department.getEmps());

        Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
        /*
        * 会从二级缓冲中查找是否有该对象
        * 只有在二级缓存中该对象多的情况下使用
        * 否则不建议使用
        * */
        Iterator iterate = query.iterate();
        while (iterate.hasNext()) {
            System.out.println(iterate.next());
        }

    }


    /*
    * 测试dao
    * */
    @Test
    public void testSession() {
        DepartmentDao departmentDao = new DepartmentDao();

        //获取session，开启事务
        Session session = HibernateUtils.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Department department = new Department();
        department.setName("aaa");
        departmentDao.save(department);
        departmentDao.save(department);
        departmentDao.save(department);

        transaction.commit();

        //当事务提交或回滚时，会将session关闭
        System.out.println(session.isOpen());

    }
}
