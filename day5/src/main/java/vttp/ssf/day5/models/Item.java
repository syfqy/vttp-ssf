package vttp.ssf.day5.models;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Item implements Serializable{

    // ******************************
    // * Instance attributes
    // ******************************

    private String id;
    public String name;
    public int qty;

    // ******************************
    // * Constructors
    // ******************************

    public Item() {
        this.qty = 1;
    }
    
    public Item(String name) {
        this();
        this.name = name;
    }

    public Item(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }

    // ******************************
    // * Getters & Setters
    // ******************************

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    
    public void setQty(int qty) {
        this.qty = qty;
    }

    // ******************************
    // * Public methods
    // ******************************

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

    public String getItemString() {
        // returns item string to be written to file
        StringBuilder sb = new StringBuilder();
        sb.append("name: " + name + ", ");
        sb.append("qty: " + String.valueOf(qty));

        return sb.toString();
    }

}

