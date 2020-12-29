package com.hibernate.mapping.many_to_one;

/**
 * @author lijichen
 * @date 2020/11/8 - 18:35
 */
public class Customer {
    private int id;
    private String name;

    public Customer() {
    }

    public Customer(String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
