package vttp.ssf.day5.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.day5.models.Cart;
import vttp.ssf.day5.models.Item;
import static vttp.ssf.day5.utils.CartIOUtil.*;

@Repository
public class CartRepository {

    private static final Logger logger = LoggerFactory.getLogger(CartRepository.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public boolean isExistingUser(final String username) {
        return redisTemplate.hasKey(username);
    }

    public void saveCart(final Cart cart) {
        // delete and overwrite cart
        redisTemplate.delete(cart.getUsername());
        if (cart.getItemList().size() > 0) {
            for (Item item : cart.getItemList()) {
                redisTemplate.opsForList().rightPush(cart.getUsername(), item.getItemString());
            }
        }
        logger.info("Saved user cart");
    }

    public Cart getCartByUsername(final String username) {
        Long nItems = redisTemplate.opsForList().size(username);
        List<Item> resultItemList = redisTemplate.opsForList()
                .range(username, 0, nItems)
                .stream()
                .map(e -> convertStrToItem((String) e))
                .collect(Collectors.toCollection(LinkedList::new));

        Cart resultCart = new Cart(username, resultItemList);
        return resultCart;
    }

}
