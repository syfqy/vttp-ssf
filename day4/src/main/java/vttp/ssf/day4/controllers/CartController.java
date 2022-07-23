package vttp.ssf.day4.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//TODO: Extract common subroutines (e.g getting user cart etc)
@Controller
public class CartController {

    @Autowired
    Cart cart;

    @Autowired
    Item itemName;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping(value = { "/" })
    public String showForm(Model model) {
        // on first page load
        model.addAttribute("cart", cart);
        model.addAttribute("item", itemName);
        return "cart";
    }

    @GetMapping(value = { "/cart" })
    public String showEmptyCart() {
        // show empty cart when no username entered
        return "frag/emptyCart :: empty-cart";
    }

    @GetMapping(value = { "/cart/{username}" })
    public String showUserCart(Model model, @PathVariable String username) {

        // if username entered, get and return user's cart list
        Cart userCart = new Cart(username);
        model.addAttribute("cart", userCart);
        logger.info("Loading cart: " + userCart);
        return "frag/userCart :: user-cart";
    }

    @PostMapping(value = { "/cart/add-item" })
    public String addItem(
            @ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        // SMELL: Need to instantiate new Cart each time since item data not sent to controller
        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // add new item to cart
        userCart.addItem(item);

        // add cart to model
        model.addAttribute("cart", userCart);
        logger.info("Added new item: " + item);

        return "cart";
    }

    @GetMapping(value = { "/cart/{username}/delete-item/" })
    public String deleteItem(
            @RequestParam(required = true) String itemName,
            @ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // delete item
        item.setName(itemName);
        userCart.deleteItem(item);

        // add cart to model
        model.addAttribute("cart", userCart);
        logger.info("Deleted item: " + item);

        return "cart";
    }

    @GetMapping(value = { "/cart/{username}/shift-item/" })
    public String shiftItem(
            @RequestParam(required = true) String itemName,
            @ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // shift item up
        item.setName(itemName);
        userCart.shiftItemUp(item);

        // add cart to model
        model.addAttribute("cart", userCart);
        logger.info("Shifted item up: %s\n", item);

        return "cart";
    }

    @GetMapping(value = { "/cart/{username}/increment-item/" })
    public String incrementItem(
            @RequestParam(required = true) String itemName,
            @ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // get item
        item.setName(itemName);
        System.out.println(item);

        // increment item qty
        userCart.incrementItem(item);

        // add cart to model
        model.addAttribute("cart", userCart);

        return "cart";
    }

    @GetMapping(value = { "/cart/{username}/decrement-item/" })
    public String decrementItem(
            @RequestParam(required = true) String itemName,
            @ModelAttribute Item item,
            @ModelAttribute Cart cart,
            Model model) {

        // Get user's cart
        String username = cart.getUsername();
        Cart userCart = new Cart(username);

        // get item
        item.setName(itemName);

        // decrement item qty
        userCart.decrementItem(item);

        // add cart to model
        model.addAttribute("cart", userCart);

        return "cart";
    }

}
