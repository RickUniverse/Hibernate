package com.hibernate.component;

/**
 * @author lijichen
 * @date 2020/11/8 - 17:01
 */
public class Worker {
    private int id;
    private String name;
    private Pay pay;


    public Worker( String name, Pay pay) {
        this.name = name;
        this.pay = pay;
    }

    public Worker() {
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

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pay=" + pay +
                '}';
    }
}
