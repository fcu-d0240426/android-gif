package com.example.user.myapplication;

/**
 * Created by caihongru on 2017/6/19.
 */

public class Temple{
    String name;
    String kind;
    String address;

    public Temple(){}

    public Temple(String name,String kind,String address){
        this.name =name;
        this.kind =kind;
        this.address =address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}