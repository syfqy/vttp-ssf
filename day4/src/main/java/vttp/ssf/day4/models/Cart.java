package vttp.ssf.day4.models;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    
    private boolean isExistingUser() {
            return userCartFile.exists();
        }
    
    private void createUserCartFile() {
        createDir(userCartFile);
    }

    private void saveCart() {
        List<String> itemStringList = itemList.stream()
                                            .map(item -> item.getItemString())
                                            .toList();
        writeItemsToFile(userCartFile, itemStringList);
        System.out.println("Cart saved");
    }

    private boolean isItemInCart(Item item) {
        List<String> itemNames = this.itemList
                                    .stream()
                                    .map(i -> i.getName())
                                    .toList();
        return itemNames.contains(item.getName());
    }

    // ******************************
    // * Public methods
    // ******************************

    public List<Item> loadCart(File userCartFile) {
        List<Item> cart = new LinkedList<Item>();

        if(isExistingUser()) {
            List<Map> cartData = readItemsFromFile(userCartFile);
            if (cartData.size() > 0) {
                for (Map<String, String> itemMap : cartData) {
                    String id = itemMap.get("id");
                    String name = itemMap.get("name");
                    int qty = Integer.parseInt(itemMap.get("qty"));
                    Item item = new Item(id, name, qty);
                    cart.add(item);
                }
            }
        }
        return cart;
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

    //TODO: throw error if item already in list
    public void addItem(Item item) {
        if(!isItemInCart(item)) {
            this.itemList.add(item);
            this.saveCart();
        }
    }

    //TODO: check item by name
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