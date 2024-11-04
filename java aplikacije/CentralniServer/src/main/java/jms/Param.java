/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import java.io.Serializable;

/**
 *
 * @author kokan
 */
public class Param implements Serializable{
    String name;
    String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Param(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}