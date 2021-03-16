package com.xiaoxu.controller;

import lombok.Data;

/**
 * @author xx
 * @create 2021/3/16 9:38
 */
@Data
public class Members{

    private String name;

    private Double star;

    public Members(String name, Double star){
        this.name = name;
        this.star = star;
    }

    public Members(){

    }
}
