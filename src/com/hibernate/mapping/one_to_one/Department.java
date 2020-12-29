package com.hibernate.mapping.one_to_one;

/**
 * @author lijichen
 * @date 2020/11/9 - 16:21
 */
public class Department {
    private Integer id;
    private String name;

    private Manager manager;

    public Department() {
    }

    public Department(Integer id, String name, Manager manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
