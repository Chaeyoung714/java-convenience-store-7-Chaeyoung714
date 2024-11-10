package store.service;

import java.util.Map;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Item;

public class OrderService {
    private final MembershipPolicy membershipPolicy;
    private final PromotionPolicy promotionPolicy;

    public OrderService(MembershipPolicy membershipPolicy, PromotionPolicy promotionPolicy) {
        this.membershipPolicy = membershipPolicy;
        this.promotionPolicy = promotionPolicy;
    }

    public void checkStock(Cart consumerCart) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            if (cart.get(item) > (item.getPromotionQuantity() + item.getRegularQuantity())) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    public void applyPromotion(Cart consumerCart, DiscountHistory discountHistory) {
        try {
            Map<Item, Integer> cart = consumerCart.getCart();
            for (Item item : cart.keySet()) {
                if (item.hasOngoingPromotion()) {
                    checkWhetherReAskToConsumer(item, cart.get(item));
                    simplyApplyPromotion(item, cart.get(item), discountHistory); // 프로모션 예외가 안터진 경우
                }
            }
        } catch (OutOfPromotionStockException | DidNotBringPromotionGiveProductException e) {
            throw e;
        }
    }

    private void checkWhetherReAskToConsumer(Item item, int buyAmount) {
        if (buyAmount > item.getPromotionQuantity()) {
            throw new OutOfPromotionStockException(item, buyAmount);
        }
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int requiredBuyAmount = item.getPromotion().get().getBuyAmount();
        if (buyAmount % promotionBundleAmount == requiredBuyAmount) {
            if ((buyAmount + 1) <= item.getPromotionQuantity()) {
                throw new DidNotBringPromotionGiveProductException(item, buyAmount);
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

    public void orderAddingOrWithoutGift(String answer, Item gift, int buyAmount, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            applyPromotionAddingGift(gift, buyAmount, cart, discountHistory);
        }
        if (answer.equals("N")) {
            simplyApplyPromotion(gift, buyAmount, discountHistory);
        }
    }

    public void orderWithOrWithoutRegularItems(String answer, Item item, int buyAmount, int outOfStockAmount, Cart cart,
                                               DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            simplyApplyPromotion(item, buyAmount, discountHistory);
        }
        if (answer.equals("N")) {
            applyPromotionWithoutRegularItems(item, buyAmount, outOfStockAmount, cart, discountHistory);
        }
    }

    public void simplyApplyPromotion(Item item, int buyAmount, DiscountHistory discountHistory) {
        //가장 기본 - 그대로 프로모션 적용
        int giftAmount = promotionPolicy.calculateGift(item, buyAmount);
        discountHistory.addGift(item, giftAmount);
    }

    private void applyPromotionWithoutRegularItems(Item item, int buyAmount, int outOfStockAmount, Cart cart,
                                                   DiscountHistory discountHistory) {
        //모자란 건 결제 안함! = outOfStock 만큼 구매하지 않음 + 결제시 프로모션만 적용
        int updatedBuyAmount = buyAmount - outOfStockAmount;
        int giftAmount = promotionPolicy.calculateGift(item, updatedBuyAmount);
        discountHistory.addGift(item, giftAmount);
        cart.deductBuyAmountOf(item, outOfStockAmount);
    }

    private void applyPromotionAddingGift(Item item, int buyAmount, Cart cart, DiscountHistory discountHistory) {
        // 증정품 추가함 = 1만큼 구매 추가 + 결제시 프로모션만 적용
        int updatedBuyAmount = buyAmount + 1;
        int giftAmount = promotionPolicy.calculateGift(item, updatedBuyAmount);
        discountHistory.addGift(item, giftAmount);
        cart.addBuyAmountOf(item, 1);
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
