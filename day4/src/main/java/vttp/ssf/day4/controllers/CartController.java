package vttp.ssf.day4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.ssf.day4.models.Cart;
import vttp.ssf.day4.models.Item;

// TODO: Replace print with logger
// TODO: Replace home route "/" with "/cart"
@Controller
public class CartController {

    @Autowired
    Cart cart;

    @Autowired
    Item itemName;

    @GetMapping(value = { "/" })
    public String showForm(Model model) {
        // on first page load
        model.addAttribute("cart", cart);
        model.addAttribute("item", itemName);
        return "cart";
    }

    @GetMapping(value = {"/cart"})
    public String showEmptyCart() {
        return "frag/emptyCart :: empty-cart";
    }

    @GetMapping(value = { "/cart/{username}" })
    public String showUserCart(Model model, @PathVariable String username) {

        // if username entered, get user's cart and return template fragment
        System.out.println("Requested for " + username);
        Cart userCart = new Cart(username);
        model.addAttribute("cart", userCart);
        System.out.println("Loading cart: " + userCart);
        return "frag/userCart :: user-cart";
    }
    
    @PostMapping(value = { "/cart/{username}/add-item/" })
    public String addItem(
        @ModelAttribute Item item,
        @ModelAttribute Cart cart,
        Model model) {
        
        // Get user's cart
        String username = cart.getUsername();
        // SMELL: Need to instantiate new Cart each time since item data not sent to controller
        Cart userCart = new Cart(username);
        
        // add new item to cart
        userCart.addItem(item);
        
        // add cart to model
        model.addAttribute("cart", userCart);
        System.out.printf("Added new item: %s\n", item);
        
        return "cart";
    }

    @GetMapping(value = { "/cart/{username}/delete-item/" })
    public String deleteItem(
        @RequestParam (required=true) String itemName,
        @ModelAttribute Item item,
        @ModelAttribute Cart cart, 
        Model model) {

        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // delete item
        item.setName(itemName);
        System.out.println("Item to delete: " + item);
        userCart.deleteItem(item);

        // add cart to model
        model.addAttribute("cart", userCart);
        System.out.printf("Deleted item: %s\n", item);

        return "cart";
    }

    @GetMapping(value = { "/cart/{username}/shift-item/" })
    public String shiftItem(
        @RequestParam (required=true) String itemName,
        @ModelAttribute Item item,
        @ModelAttribute Cart cart, 
        Model model) {

        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // shift item up
        item.setName(itemName);
        System.out.println("Item to shift: " + item);
        userCart.shiftItemUp(item);

        // add cart to model
        model.addAttribute("cart", userCart);
        System.out.printf("Shifted item: %s\n", item);

        return "cart";
    }



}
