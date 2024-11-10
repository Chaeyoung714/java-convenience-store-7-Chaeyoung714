package store.service;

import java.util.Map;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Item;

public class OrderService {
    private final MembershipPolicy membershipPolicy;

    public OrderService(MembershipPolicy membershipPolicy) {
        this.membershipPolicy = membershipPolicy;
    }

    public void checkStock(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            if (cart.get(item) > (item.getPromotionQuantity() + item.getRegularQuantity())) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    // 프로모션 적용과, 할인, 상품 실제 결제를 따로 한다.
    public void orderItems(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            item.purchase(cart.get(item));
        }
    }

    public void applyMemberShip(String answer, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            int totalCost = cart.getTotalCost();
            int promotionAppliledAmount = discountHistory.getPromotionAppliedAmount();
            int membershipAmount = membershipPolicy.applyMembership(totalCost - promotionAppliledAmount);
            discountHistory.addMembershipDiscount(membershipAmount);
        }
    }
}
