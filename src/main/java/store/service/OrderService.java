package store.service;

import static store.exceptions.ExceptionMessages.ORDER_EXCEEDS_STOCK_QUANTITY;
import static store.exceptions.ExceptionMessages.WRONG_ORDER_FORMAT;

import java.util.HashMap;
import java.util.Map;
import store.exceptions.NotFoundByNameException;
import store.model.consumer.Cart;
import store.model.item.Item;
import store.model.item.Items;
import store.util.Parser;

public class OrderService {

    public Cart registerOrder(String orderDetails, Items items) {
        Map<String, String> parsedOrderDetails = Parser.parseOrderDetails(orderDetails);
        try {
            Map<Item, Integer> cart = new HashMap<>();
            for (String itemName : parsedOrderDetails.keySet()) {
                Item item = items.findByName(itemName);
                int buyAmount = Integer.parseInt(parsedOrderDetails.get(itemName));
                cart.put(item, buyAmount);
            }
            return Cart.of(cart, items);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
        } catch (NotFoundByNameException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void checkStock(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            if (cart.get(item) > (item.getPromotionQuantity() + item.getRegularQuantity())) {
                throw new IllegalArgumentException(ORDER_EXCEEDS_STOCK_QUANTITY.getMessage());
            }
        }
    }

    public void orderItems(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            item.purchase(cart.get(item));
        }
    }
}
