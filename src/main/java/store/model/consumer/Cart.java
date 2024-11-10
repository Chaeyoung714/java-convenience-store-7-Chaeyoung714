package store.model.consumer;

import static store.exceptions.ExceptionMessages.WRONG_ORDER_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import store.exceptions.ExceptionMessages;
import store.exceptions.NotFoundByNameException;
import store.model.item.Item;
import store.model.item.Items;

public class Cart{
    private final Map<Item, Integer> cart;

    private Cart(Map<Item, Integer> cart) {
        validatePositiveNumber(cart.values());
        this.cart = cart;
    }

    public static Cart of(Map<Item, Integer> cart, Items items) {
        validateExistingItem(cart.keySet(), items);
        return new Cart(cart);
    }

    private void validatePositiveNumber(Collection<Integer> buyAmounts) {
        for (int buyAmount : buyAmounts) {
            if (buyAmount <= 0) {
                throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
            }
        }
    }

    private static void validateExistingItem(Set<Item> cartItems, Items items) {
        try {
            cartItems.stream()
                    .map(Item::getName)
                    .forEach(items::findByName);
        } catch (NotFoundByNameException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void deductBuyAmountOf(Item item, int deductAmount) {
        if (cart.get(item) < deductAmount) {
            throw new IllegalStateException("[SYSTEM] Cannot Deduct Buy Amount Under Zero");
        }
        cart.replace(item, cart.get(item) - deductAmount);
    }

    public void addBuyAmountOf(Item item, int addAmount) {
        cart.replace(item, cart.get(item) + addAmount);
    }

    public int calculateTotalCost() {
        int totalCost = 0;
        for (Item item : cart.keySet()) {
            totalCost += (cart.get(item) * item.getPrice());
        }
        return totalCost;
    }

    public int calculateTotalBuyAmount() {
        int totalBuyAmount = 0;
        for (Item item : cart.keySet()) {
            totalBuyAmount += cart.get(item);
        }
        return totalBuyAmount;
    }

    public Map<Item, Integer> getCart() {
        return Collections.unmodifiableMap(cart);
    }
}
