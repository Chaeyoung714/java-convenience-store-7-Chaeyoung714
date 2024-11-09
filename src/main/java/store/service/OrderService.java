package store.service;

import java.util.Map;
import store.Promotions;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
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

    public void applyPromotion(Cart consumerCart, PromotionPolicy promotionPolicy) {
        try {
            Map<Item, Integer> cart = consumerCart.getCart();
            for (Item item : cart.keySet()) {
                if (item.hasOngoingPromotion()) {
                    checkWhetherReAskToConsumer(item, cart.get(item));
                    simplyApplyPromotion(promotionPolicy, item, cart.get(item)); // 프로모션 예외가 안터진 경우
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

    public void orderAddingOrWithoutGift(String answer, PromotionPolicy promotionPolicy, Item gift, int buyAmount,
                                         Cart cart) {
        if (answer.equals("Y")) {
            orderAddingGift(promotionPolicy, gift, buyAmount, cart);
        }
        if (answer.equals("N")) {
            orderWithoutGift(promotionPolicy, gift, buyAmount);
        }
    }

    public void orderWithOrWithoutRegularItems(String answer, PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        if (answer.equals("Y")) {
            orderWithRegularItems(promotionPolicy, item, buyAmount);
        }
        if (answer.equals("N")) {
            orderWithoutRegularItems(promotionPolicy, item, buyAmount, cart);
        }
    }

    public void simplyApplyPromotion(PromotionPolicy promotionPolicy, Item item, int buyAmount) {
        //가장 기본 - 그대로 프로모션 적용
        promotionPolicy.addGift(item, buyAmount);
    }

    private void orderWithRegularItems(PromotionPolicy promotionPolicy, Item item, int buyAmount) {
        //모자란 건 프로모션 적용 안함! = 이대로 결제 + 결제시 프로모션과 정가 모두 적용
        promotionPolicy.addGift(item, buyAmount);
    }

    private void orderWithoutRegularItems(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        //모자란 건 결제 안함! = outOfStock 만큼 구매하지 않음 + 결제시 프로모션만 적용
        int bundleAmount = item.getPromotion().get().getBundleAmount();
        int outOfStockAmount = buyAmount - (bundleAmount * (item.getPromotionQuantity() / bundleAmount));
        int updatedBuyAmount = buyAmount - outOfStockAmount;
        promotionPolicy.addGift(item, updatedBuyAmount);
        cart.deductBuyAmountOf(item, outOfStockAmount);
    }

    private void orderAddingGift(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        // 증정품 추가함 = 1만큼 구매 추가 + 결제시 프로모션만 적용
        int updatedBuyAmount = buyAmount + 1;
        promotionPolicy.addGift(item, updatedBuyAmount);
        cart.addBuyAmountOf(item, 1);
    }

    private void orderWithoutGift(PromotionPolicy promotionPolicy, Item item, int buyAmount) {
        // 증정품 추가하지 않음 = 이대로 결제 + 결제시 프로모션만 적용
        promotionPolicy.addGift(item, buyAmount);
    }

    public void applyMemberShip(String answer, PromotionPolicy promotionPolicy, MembershipPolicy membershipPolicy, Cart cart) {
        if (answer.equals("Y")) {
            int totalCost = cart.getTotalCost();
            int promotionAppliledAmount = promotionPolicy.getPromotionAppliedAmount();
            membershipPolicy.applyMembership(totalCost - promotionAppliledAmount);
        }
    }
}
