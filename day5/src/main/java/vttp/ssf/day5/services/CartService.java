package vttp.ssf.day5.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.day5.models.Cart;
import vttp.ssf.day5.models.Item;
import vttp.ssf.day5.repositories.CartRepository;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    CartRepository cRepo;

    public Cart getUserCart(String username) {
        if (cRepo.isExistingUser(username)) {
            Cart userCart = cRepo.getCartByUsername(username);
            return userCart;
        }
        Cart userCart = new Cart(username);
        cRepo.saveCart(userCart);
        return userCart;
    }

    public Cart addItem(String username, Item itemToAdd) {
        Cart userCart = cRepo.getCartByUsername(username);
        userCart.addItem(itemToAdd);
        cRepo.saveCart(userCart);
        return userCart;
    }

    public Cart deleteItem(String username, Item itemToDelete) {
        Cart userCart = cRepo.getCartByUsername(username);
        userCart.deleteItem(itemToDelete);
        cRepo.saveCart(userCart);
        return userCart;
    }

    public Cart shiftItemUp(String username, Item itemToShift) {
        Cart userCart = cRepo.getCartByUsername(username);
        userCart.shiftItemUp(itemToShift);
        cRepo.saveCart(userCart);
        return userCart;
    }

    public Cart incrementItem(String username, Item itemToIncrement) {
        Cart userCart = cRepo.getCartByUsername(username);
        userCart.incrementItem(itemToIncrement);
        cRepo.saveCart(userCart);
        return userCart;
    }

    public Cart decrementItem(String username, Item itemToDecrement) {
        Cart userCart = cRepo.getCartByUsername(username);
        userCart.decrementItem(itemToDecrement);
        cRepo.saveCart(userCart);
        return userCart;
    }

}
