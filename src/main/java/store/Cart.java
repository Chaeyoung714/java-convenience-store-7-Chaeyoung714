package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<String, Integer> cart;

    public Cart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public List<String> getAllProductNames() {
        return cart.keySet().stream().toList();
    }

    public int getBuyCountByName(String name) {
        return cart.get(name);
    }

    public Map<String, Integer> getCart() {
        return cart;
    }
}
