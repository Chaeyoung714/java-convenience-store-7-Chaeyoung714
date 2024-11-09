package store.service;

import java.util.Map;
import store.Promotion;
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

    public void orderIncludingRegularItems(PromotionPolicy promotionPolicy, Item item, int buyAmount) {
        //모자란 건 프로모션 적용 안함! = 이대로 결제 + 결제시 프로모션과 정가 모두 적용
        promotionPolicy.addGift(item, buyAmount);
    }

    public void orderExcludingRegularItems(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
    //모자란 건 결제 안함! = outOfStock 만큼 구매하지 않음 + 결제시 프로모션만 적용
        int outOfStockAmount = buyAmount - item.getPromotionQuantity();
        int updatedBuyAmount = item.getPromotionQuantity();
        promotionPolicy.addGift(item, updatedBuyAmount);
        cart.deductBuyAmountOf(item, outOfStockAmount);
    }

    public void orderAddingGift(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        int updatedBuyAmount = buyAmount + 1;
        promotionPolicy.addGift(item, updatedBuyAmount);
        cart.addBuyAmountOf(item, 1);
    }

    public void orderExcludingGift(PromotionPolicy promotionPolicy, Item item, int buyAmount) {
        promotionPolicy.addGift(item, buyAmount);
    }
}
