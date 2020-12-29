package com.hibernate.strategy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/11/8 - 18:35
 */
public class Customer {
    private int id;
    private String name;

    /*
    * 1,声明集合类型是须使用接口类型，因为hibernate在获取集合类型时，返回的是hibernate
    *   内置的集合类型，而不是一个javaEE标准的集合实现
    * 2,双向一对多
    *   需要将hashset初始化
    * */
    private Set<Order> orders = new HashSet<>();

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }
}
