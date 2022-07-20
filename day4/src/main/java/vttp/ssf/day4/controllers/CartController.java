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

    @GetMapping(value = { "/cart"})
    public String showCart(Model model) {
        // on first page load
        model.addAttribute("cart", cart);
        model.addAttribute("item", item);
        return "cart";
    }
    
    @GetMapping(value = "/cart/{username}")
    public String showUserCart(Model model, @PathVariable String username) {
        // if username entered, get user's cart and return template fragment
        if (username != null && username != "") {
            System.out.println("Requested for " + username);
            Cart userCart = new Cart(username);
            model.addAttribute("cart", userCart);
            System.out.println("Loading cart: " + userCart);
            return "frag/userCart :: user-cart";
        }
        return "frag/emptyCart :: empty-cart";
    }
    
    // TODO: handle if item already in cart
    @PostMapping(value = { "/add-item" })
    public String addItem(@ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        // Get user's cart
        String username = cart.getUsername();
        // SMELL: Instantiating new Cart and reading file after every item added?
        Cart userCart = new Cart(username);

        // add new item to cart
        userCart.addItem(item);

        // save cart to file
        model.addAttribute("cart", userCart);
        System.out.printf("Added new item: %s\n", item);
        System.out.println("returning added user cart");

        return "frag/userCart :: user-cart";
    }

    @PostMapping(value = { "/delete-item" })
    public String deleteItem(@ModelAttribute Item item, @ModelAttribute Cart cart, Model model) {
        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // delete item
        userCart.deleteItem(item);

        return "frag/userCart :: user-cart";
    }

}
