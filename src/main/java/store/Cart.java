package store;

import java.util.Map;

public class Cart {
    private Map<String, Integer> cart;

    public Cart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public Map<String, Integer> getCart() {
        return cart;
    }
}
