package store.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import store.exceptions.NotFoundByNameException;

public class Cart{
    private final Map<Item, Integer> cart;

    private Cart(Map<Item, Integer> cart) {
        this.cart = cart;
    }

    public static Cart of(String orderDetails) {
        try {
            Map<String, String> parsedOrderDetails = parseItems(orderDetails);
            Map<Item, Integer> cart = new HashMap<>();
            for (String itemName : parsedOrderDetails.keySet()) {
                Item item = Items.findByName(itemName);
                int buyAmount = Integer.parseInt(parsedOrderDetails.get(itemName));
                validatePositiveNumber(buyAmount);
                cart.put(item, buyAmount);
            }
            return new Cart(cart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        } catch (NotFoundByNameException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private static Map<String, String> parseItems(String orderDetailInput) {
        try {
            Map<String, String> parsedOrderDetails = new HashMap<>();
            String[] orderDetails = orderDetailInput.split(",");
            for (String orderDetail : orderDetails) {
                validateBracketDelimiter(orderDetail);
                String[] parsedOrderDetail = orderDetail.substring(1, orderDetail.length() - 1)
                        .split("-");
                validateHyphenDelimiter(parsedOrderDetail);
                parsedOrderDetails.put(parsedOrderDetail[0], parsedOrderDetail[1]);
            }
            return parsedOrderDetails;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
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

    private static void validateBracketDelimiter(String orderDetail) {
        if (!orderDetail.startsWith("[") || !orderDetail.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateHyphenDelimiter(String[] parsedOrder) {
        if (parsedOrder.length != 2) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validatePositiveNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public Map<Item, Integer> getCart() {
        return Collections.unmodifiableMap(cart);
    }
}
