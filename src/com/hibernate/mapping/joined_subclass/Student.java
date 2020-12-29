package com.hibernate.mapping.joined_subclass;

/**
 * @author lijichen
 * @date 2020/11/9 - 19:43
 */
public class Student extends Person {
    private String school;

    public Student() {
    }

    public Student(Integer id, int age, String name, String school) {
        super(id, age, name);
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "school='" + school + '\'' +
                '}';
    }
}
