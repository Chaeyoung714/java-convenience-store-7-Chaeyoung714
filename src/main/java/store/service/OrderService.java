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
        } catch (OutOfPromotionStockException | NotAddGiftException e) {
            throw e;
        }
    }

    public void checkWhetherReAskToConsumer(Item item, int buyAmount) {
        if (buyAmount > item.getPromotionQuantity()) {
            throw new OutOfPromotionStockException(item, buyAmount);
        }
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int requiredBuyAmount = item.getPromotion().get().getBuyAmount();
        if (buyAmount % promotionBundleAmount == requiredBuyAmount) {
            if ((buyAmount + 1) <= item.getPromotionQuantity()) {
                throw new NotAddGiftException(item, buyAmount);
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

    public void simplyApplyPromotion(Item item, int buyAmount, DiscountHistory discountHistory) {
        //가장 기본 - 그대로 프로모션 적용
        int giftAmount = promotionPolicy.calculateGift(item, buyAmount);
        discountHistory.addGift(item, giftAmount);
    }

    public void applyPromotionWithoutRegularItems(OutOfStockPromotionDto dto, Cart cart,
                                                   DiscountHistory discountHistory) {
        //모자란 건 결제 안함! = outOfStock 만큼 구매하지 않음 + 결제시 프로모션만 적용
        int updatedBuyAmount = dto.buyAmount() - dto.outOfStockAmount();
        int giftAmount = promotionPolicy.calculateGift(dto.item(), updatedBuyAmount);
        discountHistory.addGift(dto.item(), giftAmount);
        cart.deductBuyAmountOf(dto.item(), dto.outOfStockAmount());
    }

    public void applyPromotionAddingGift(GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        // 증정품 추가함 = 1만큼 구매 추가 + 결제시 프로모션만 적용
        int updatedBuyAmount = dto.buyAmount() + 1;
        int giftAmount = promotionPolicy.calculateGift(dto.gift(), updatedBuyAmount);
        discountHistory.addGift(dto.gift(), giftAmount);
        cart.addBuyAmountOf(dto.gift(), 1);
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
