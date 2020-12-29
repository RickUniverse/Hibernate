package com.hibernate.component;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author lijichen
 * @date 2020/11/8 - 17:23
 */
@Entity
@Table(name = "workers", schema = "hibernate5", catalog = "")
public class WorkersEntity {
    private int id;
    private String name;
    private Double yearplay;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "yearplay")
    public Double getYearplay() {
        return yearplay;
    }

    public void setYearplay(Double yearplay) {
        this.yearplay = yearplay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkersEntity that = (WorkersEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(yearplay, that.yearplay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, yearplay);
    }
}
