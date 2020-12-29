package com.hibernate.helloworld2;

import java.util.Date;

/**
 * @author lijichen
 * @date 2020/11/6 - 17:51
 */
public class News {
    private int id;
    private String title;
    private String author;
    private String passowrd;
    private Date date;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public News() {
    }

    public News(int id, String title, String author, String passowrd, Date date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.passowrd = passowrd;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", passowrd='" + passowrd + '\'' +
                ", date=" + date +
                '}';
    }
}
