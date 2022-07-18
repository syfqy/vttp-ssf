package vttp.ssf.day4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vttp.ssf.day4.models.Cart;
import vttp.ssf.day4.models.Item;

@Controller
// @RequestMapping(path = "/cart")
public class CartController {

    @Autowired
    Cart cart;

    @Autowired
    Item item;
    
    @GetMapping(value={"/cart", "/cart/{username}"})
    public String showUserCart(Model model, @PathVariable(required = false) String username) {
        model.addAttribute("cart", cart);
        model.addAttribute("item", item);

        if (username != null) {
            System.out.println("Requested for " + username);
            model.addAttribute("cart", generateTestCart(username));
            return "frag/userCart :: user-cart";
        } 
        System.out.println("returning cart");
        return "cart";
    }

    // public String addItem(@ModelAttribute Item item, 
    //                         @ModelAttribute Cart cart, 
    //                         Model model) {
    //     System.out.printf("Added new item: id%d %s\n", item.getId(), item);
    //     return "cart";
    // }

    private Cart generateTestCart(String username) {
        Cart userCart = new Cart(username);
        Item i1 = new Item("test1", 1);
        Item i2 = new Item("test2", 3);
        userCart.addItem(i1);
        userCart.addItem(i2);
        return userCart;
    }

}
