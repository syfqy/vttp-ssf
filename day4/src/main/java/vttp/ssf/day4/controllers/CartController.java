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
public class CartController {

    @Autowired
    Cart cart;

    @Autowired
    Item item;

    @GetMapping(value = { "/cart", "/cart/{username}" })
    public String showUserCart(Model model, @PathVariable(required = false) String username) {

        model.addAttribute("cart", cart);
        model.addAttribute("item", item);

        // if username entered, get user's cart and return template fragment
        if (username != null) {
            System.out.println("Requested for " + username);
            model.addAttribute("cart", generateTestCart(username)); // TODO: Replace with query via model
            System.out.println("returning user cart");
            return "frag/userCart :: user-cart";
        }

        System.out.println("returning empty cart");
        return "cart";
    }

    @PostMapping(value = { "/cart", "/cart/{username}" })
    public String addItem(@ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        String username = cart.getUsername();
        Cart userCart = generateTestCart(username);
        userCart.addItem(item);

        for (Item it : cart.getItemList()) {
            System.out.println(it);
        }

        model.addAttribute("cart", userCart);
        System.out.printf("Added new item: id%d %s\n", item.getId(), item);
        System.out.println("returning added user cart");

        return "frag/userCart :: user-cart";
    }

    // TODO: Remove after controller complete
    private Cart generateTestCart(String username) {
        Cart userCart = new Cart(username);
        Item i1 = new Item("test1", 1);
        Item i2 = new Item("test2", 3);
        userCart.addItem(i1);
        userCart.addItem(i2);
        return userCart;
    }

}
