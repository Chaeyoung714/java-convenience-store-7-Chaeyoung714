package store;

import java.util.Map;

public class Cart {
    private final Map<Product, Integer> cart;

    public Cart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public void checkStock() {
        for (Product product : cart.keySet()) {
            int totalStockQuantity = product.getPromotionQuantity() + product.getRegularQuantity();
            if (totalStockQuantity < cart.get(product)) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    public void addBuyAmountOf(Product product) {
        if (cart.containsKey(product)) {
            cart.replace(product, cart.get(product) + 1);
        }
        throw new IllegalArgumentException("No such product in cart");
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }
}
