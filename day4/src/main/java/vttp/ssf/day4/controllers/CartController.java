package vttp.ssf.day4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.day4.models.Cart;
import vttp.ssf.day4.models.Item;

@Controller
@RequestMapping(path = "/")
public class CartController {

    @Autowired
    Cart cart;

    // @Autowired
    // Item item;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        // model.addAttribute("item", item);
        model.addAttribute("item", new Item());
        return "index";
    }

    @PostMapping
    public String addItem(@ModelAttribute Item item, 
                            @ModelAttribute Cart cart, 
                            Model model) {
        System.out.printf("Added new item: id%d %s\n", item.getId(), item);
        return "index";
    }

    //TODO: show user's cart

}
