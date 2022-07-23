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
        logger.info("Created new cart for: " + username);
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
        logger.info("Cart saved");
    }

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

    public List<Item> loadCart(File userCartFile) {
        List<Item> cart = new LinkedList<Item>();

        if (isExistingUser()) {
            List<Map> cartData = readItemsFromFile(userCartFile);
            if (cartData.size() > 0) {
                for (Map<String, String> itemMap : cartData) {
                    String name = itemMap.get("name");
                    int qty = Integer.parseInt(itemMap.get("qty"));
                    Item item = new Item(name, qty);
                    cart.add(item);
                }
            }
        }
        return cart;
    }

    public void printCart() {
        for (Item item : itemList) {
            logger.info(item.toString());
        }
    }

    public void addItem(Item itemToAdd) {
        if (!isItemInCart(itemToAdd)) {
            itemList.add(itemToAdd);
            saveCart();
        }
    }

    // TODO: throw error if item not in list
    public void deleteItem(Item itemToDelete) {
        int idxToDelete = getIdxOfItem(itemToDelete);
        itemList.remove(idxToDelete);
        saveCart();
    }

    public void shiftItemUp(Item itemToShift) {
        int i = getIdxOfItem(itemToShift);
        int j = i - 1;

        // swap indexes of curr item and item before in list
        if (i > 0) {
            Collections.swap(itemList, i, j);
        }
        saveCart();
    }

    public void incrementItem(Item itemToIncrement) {
        int i = getIdxOfItem(itemToIncrement);
        Item item = itemList.get(i);
        item.incrementQty();
        saveCart();
    }

    public void decrementItem(Item itemToDecrement) {
        int i = getIdxOfItem(itemToDecrement);
        Item item = itemList.get(i);
        item.decrementQty();
        if (item.getQty() > 0) {
            saveCart();
        } else {
            deleteItem(item);
        }
        
    }

    public void clearCart() {
        itemList.clear();
    }

    @Override
    public String toString() {
        return "Cart username=" + username + " userCartFile=" + userCartFile;
    }

}