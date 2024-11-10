package store.service;

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
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        } catch (NotFoundByNameException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void checkStock(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            if (cart.get(item) > (item.getPromotionQuantity() + item.getRegularQuantity())) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
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
