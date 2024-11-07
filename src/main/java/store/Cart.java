package store;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cart;

    public Cart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }
}
