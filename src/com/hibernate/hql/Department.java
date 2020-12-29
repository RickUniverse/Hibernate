package com.hibernate.hql;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/11/11 - 14:54
 */
public class Department {
    private Integer id;
    private String name;

    private Set<Employee> emps = new HashSet<>();

    public Department() {
    }

    public Department(Integer id, String name, Set<Employee> emps) {
        this.id = id;
        this.name = name;
        this.emps = emps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmps() {
        return emps;
    }

    public void setEmps(Set<Employee> emps) {
        this.emps = emps;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
