package vttp.ssf.day5.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Cart implements Serializable {

    // ******************************
    // * Instance attributes
    // ******************************

    private String username;
    public List<Item> itemList;

    // ******************************
    // * Constructors
    // ******************************

    public Cart() {
        this.itemList = new LinkedList<Item>();
    }

    public Cart(String username) {
        this();
        this.username = username;
    }

    public Cart(String username, List<Item> itemList) {
        this.username = username;
        this.itemList = itemList;
    }

    // ******************************
    // * Getters & Setters
    // ******************************

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    // ******************************
    // * Private methods
    // ******************************


    private List<String> getItemNames() {
        List<String> itemNames = this.itemList
                .stream()
                .map(i -> i.getName())
                .toList();
        return itemNames;
    }

    private boolean isItemInCart(Item item) {
        List<String> itemNames = this.getItemNames();
        return itemNames.contains(item.getName());
    }

    private int getIdxOfItem(Item item) {
        List<String> itemNames = this.getItemNames();
        return itemNames.indexOf(item.getName());
    }

    // ******************************
    // * Public methods
    // ******************************


    public void printCart() {
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    public void addItem(Item itemToAdd) {
        if (!isItemInCart(itemToAdd)) {
            itemList.add(itemToAdd);
        }
    }

    public void deleteItem(Item itemToDelete) {
        int idxToDelete = getIdxOfItem(itemToDelete);
        itemList.remove(idxToDelete);
    }

    public void shiftItemUp(Item itemToShift) {
        int i = getIdxOfItem(itemToShift);
        int j = i - 1;

        // swap indexes of curr item and item before in list
        if (i > 0) {
            Collections.swap(itemList, i, j);
        }
    }

    public void incrementItem(Item itemToIncrement) {
        int i = getIdxOfItem(itemToIncrement);
        Item item = itemList.get(i);
        item.incrementQty();
    }

    public void decrementItem(Item itemToDecrement) {
        int i = getIdxOfItem(itemToDecrement);
        Item item = itemList.get(i);
        item.decrementQty();
        if (item.getQty() <= 0) {
            deleteItem(item);
        }
    }

    public void clearCart() {
        itemList.clear();
    }

}