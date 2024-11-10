package store.service;

import store.discountPolicy.PromotionPolicy;
import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Item;

public class PromotionService {
    private final PromotionPolicy promotionPolicy;

    public PromotionService(PromotionPolicy promotionPolicy) {
        this.promotionPolicy = promotionPolicy;
    }

    public void checkAndApplyPromotion(Item item, int buyAmount, DiscountHistory discountHistory) {
        if (item.hasOngoingPromotion()) {
            checkWhetherNotifyConsumer(item, buyAmount);
            applyDefaultPromotion(item, buyAmount, discountHistory); // 프로모션 예외가 안터진 경우
        }
    }

    public void checkWhetherNotifyConsumer(Item item, int buyAmount) {
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

    public void applyDefaultPromotion(Item item, int buyAmount, DiscountHistory discountHistory) {
        int giftAmount = promotionPolicy.calculateGift(item, buyAmount);
        discountHistory.addGift(item, giftAmount);
    }

    public void applyPromotionWithoutRegularItems(OutOfStockPromotionDto dto, Cart cart,
                                                  DiscountHistory discountHistory) {
        int updatedBuyAmount = dto.buyAmount() - dto.outOfStockAmount();
        int giftAmount = promotionPolicy.calculateGift(dto.item(), updatedBuyAmount);
        discountHistory.addGift(dto.item(), giftAmount);
        cart.deductBuyAmountOf(dto.item(), dto.outOfStockAmount());
    }

    public void applyPromotionAddingGift(GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        int updatedBuyAmount = dto.buyAmount() + 1;
        int giftAmount = promotionPolicy.calculateGift(dto.gift(), updatedBuyAmount);
        discountHistory.addGift(dto.gift(), giftAmount);
        cart.addBuyAmountOf(dto.gift(), 1);
    }
}
