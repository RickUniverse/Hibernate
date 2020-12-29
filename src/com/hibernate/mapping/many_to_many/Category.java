package com.hibernate.mapping.many_to_many;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/11/9 - 18:15
 */
public class Category {
    private int id;
    private String name;

    private Set<Item> items = new HashSet<>();

    public Category() {
    }

    public Category(int id, String name, Set<Item> items) {
        this.id = id;
        this.name = name;
        this.items = items;
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
