/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

/**
 *
 * @author kokan
 */
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Request implements Serializable{
    private final Queue<Param> queue;

    public Request() {
        this.queue = new LinkedList<>();
    }

    public void add(String name, String value) {
        Param pair = new Param(name, value);
        queue.add(pair);
    }

    public Param get() {
        return queue.poll();
    }

    public Param peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
