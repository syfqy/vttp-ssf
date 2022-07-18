package vttp.ssf.day4.models;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Cart implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Cart.class);
    private static final String defaultDataDir = System.getProperty("user.dir") + "/data";

    private String username;
    private File userCartFile;
    public List<Item> itemList;
    
    public Cart() {
        this.itemList = new LinkedList<Item>();
    }

    public Cart(String username) {
        this.username = username;
        this.userCartFile = getUserCartFile(username);
        this.itemList = loadCart(userCartFile);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public File getUserCartFile() {
        return userCartFile;
    }

    public void setUserCartFile(File userCartFile) {
        this.userCartFile = userCartFile;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
    private File getUserCartFile(String username) {
        return new File(defaultDataDir + "/" + username);
    }

    private boolean isExistingUser() {
        return userCartFile.exists();
    }

    private List<String> retrieveCartData(File userCartFile) {
        List<String> cartData = new LinkedList<String>();
        try {
            cartData = Files.readAllLines(userCartFile.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return cartData;
    }

    public List<Item> loadCart(File userCartFile) {
        List<Item> itemList = new LinkedList<Item>();

        // read data from user file and rebuild item list
        if (isExistingUser()) {
            List<String> cartData = retrieveCartData(userCartFile);
            if (cartData.size() > 0) {
                for (String itemData : cartData) {
                    String[] itemDataSplit = itemData.split("x ");
                    int itemQty = Integer.parseInt(itemDataSplit[0]);
                    String itemName = itemDataSplit[1];
                    Item item = new Item(itemName, itemQty);
                    itemList.add(item);
                }
            }
        }

        return itemList;
    }

    public void displayCart() {
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    public void saveCart() {
        System.out.println("Cart saved");
    }

    public void moveItemUp(Item item) {
        int i = itemList.indexOf(item);
        int j = i - 1;

        if (i > 0) {
            Collections.swap(itemList, i, j);
        }
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }

    public void deleteItem(Item item) {
        this.itemList.remove(item);
    }

    public void clearCart() {
        itemList.clear();
    }

}