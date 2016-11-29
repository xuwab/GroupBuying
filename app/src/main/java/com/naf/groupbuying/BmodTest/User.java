package com.naf.groupbuying.BmodTest;

import cn.bmob.v3.BmobObject;


import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/29.
 */

public class User extends BmobObject{
    private String name;
    private int age;

    private Student student;
    private Worker worker;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
