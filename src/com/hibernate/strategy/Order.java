package com.hibernate.strategy;

/**
 * @author lijichen
 * @date 2020/11/8 - 18:34
 */
public class Order {
    private int id;
    private Customer customer;

    public Order() {
    }

    public Order(Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }
}
