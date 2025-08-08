package org.example.powerpark.model;

import org.example.powerpark.businesslogic.service;

public  abstract class Person {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String phone_num;

    public Person(){

    }
    public Person(int id, String username, String password,String email, String phone_num) {
        this.id=id;
        this.username = username;
        this.password=password;
        this.email = email;
        this.phone_num = phone_num;
    }
    public Person( String username, String password, String email, String phone_num) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_num = phone_num;
    }
    public int getIdByUsername(String username,String role){
        service getId = new service();
        int id=getId.getIdByUsername(username,role);
        return id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
