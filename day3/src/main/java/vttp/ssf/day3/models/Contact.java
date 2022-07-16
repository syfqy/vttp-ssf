package vttp.ssf.day3.models;

import java.io.Serializable;
import java.util.Random;

public class Contact implements Serializable {
    private String name;
    private String email;
    private int phoneNum;
    private String id;
    
    public Contact() {
        this.id = generateId(8);
    }

    public Contact(String name, String email, int phoneNum) {
        this.id = generateId(8);
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Contact(String id, String name, String email, int phoneNum) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // prevent concurrent calls
    private synchronized String generateId(int numChars) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() <  numChars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numChars);
    }
}
