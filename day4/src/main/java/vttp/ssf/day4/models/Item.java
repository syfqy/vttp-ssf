package vttp.ssf.day4.models;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Item implements Serializable{

    // ******************************
    // * Instance attributes
    // ******************************
    private static int count = 0; // TODO: Replace with randomly generated uuid
    private int id;
    public String name;
    public int qty;

    // ******************************
    // * Constructors
    // ******************************

    public Item() {
        this.id = count;
        this.qty = 1;
        count++;
    }
    
    public Item(String name) {
        this();
        this.name = name;
    }

    public Item(String name, int qty) {
        this(name);
        this.qty = qty;
    }

    // ******************************
    // * Getters & Setters
    // ******************************

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

    public int getQty() {
        return qty;
    }

    // ******************************
    // * Public methods
    // ******************************

    public void setQty(int qty) {
        this.qty = qty;
    }


    public void incrementQty() {
        this.qty++;
    }

    public void decrementQty() {
        if(this.qty > 0) {
            this.qty--;
        }

    }
    public void deleteItem() {
        this.qty = 0;
    }

    @Override
    public String toString() {
        return this.qty + "x " + this.name;
    }

}

