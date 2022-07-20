package vttp.ssf.day4.models;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static vttp.ssf.day4.utils.CartIOUtil.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Cart implements Serializable {

    // ******************************
    // * Class attributes
    // ******************************

    private static final Logger logger = LoggerFactory.getLogger(Cart.class);
    private static final String defaultDataDir = System.getProperty("user.dir") + "/data";

    // ******************************
    // * Instance attributes
    // ******************************
    
    private String username;
    private File userCartFile;
    public List<Item> itemList;

    // ******************************
    // * Constructors
    // ******************************

    public Cart() {
        this.itemList = new LinkedList<Item>();
    }

    public Cart(String username) {
        this.username = username;
        this.userCartFile = new File(defaultDataDir + "/" + username);
        this.createUserCartFile();
        this.itemList = loadCart(userCartFile);
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

    // ******************************
    // * Private methods
    // ******************************
    
    private void createUserCartFile() {
        createDir(userCartFile);
    }

    private void saveCart() {
        List<String> itemStringList = itemList.stream()
                                            .map(item -> item.toString())
                                            .toList();
        writeToFile(userCartFile, itemStringList);
        System.out.println("Cart saved");
    }

    private boolean isExistingUser() {
        return userCartFile.exists();
    }

    // ******************************
    // * Public methods
    // ******************************

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
                    System.out.println(itemDataSplit);
                    int itemQty = Integer.parseInt(itemDataSplit[0].trim());
                    String itemName = itemDataSplit[1];
                    Item item = new Item(itemName, itemQty);
                    itemList.add(item);
                }
            }
        }

        return itemList;
    }

    public void printCart() {
        for (Item item : itemList) {
            System.out.println(item);
        }
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
        this.saveCart();
    }

    public void deleteItem(Item item) {
        this.itemList.remove(item);
        this.saveCart();
    }

    public void clearCart() {
        this.itemList.clear();
    }

    @Override
    public String toString() {
        return "Cart username=" + username + " userCartFile=" + userCartFile;
    }

}