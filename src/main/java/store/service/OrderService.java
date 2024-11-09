package store.service;

import java.util.Map;
import store.discountPolicy.PromotionPolicy;
import store.model.Cart;
import store.model.Item;

public class OrderService {
    public void checkStock(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            if (cart.get(item) > (item.getPromotionQuantity() + item.getRegularQuantity())) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    public void applyPromotion(PromotionPolicy promotionPolicy, Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            if (item.hasOngoingPromotion()) {
                promotionPolicy.applyPromotion(item, cart.get(item));
            }
        }
    }

    // 프로모션 적용과, 할인, 상품 실제 결제를 따로 한다.
    public void orderItems(Cart cart) {

    }
}
