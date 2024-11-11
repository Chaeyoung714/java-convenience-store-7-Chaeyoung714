package store.service;

import store.discountPolicy.PromotionPolicy;
import store.dto.promotion.GiftDto;
import store.dto.promotion.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.item.Item;

public class PromotionService {
    private final PromotionPolicy promotionPolicy;

    public PromotionService(PromotionPolicy promotionPolicy) {
        this.promotionPolicy = promotionPolicy;
    }

    public void checkAndApplyPromotion(Item item, int buyAmount, DiscountHistory discountHistory) {
        if (item.hasOngoingPromotion()) {
            checkWhetherNotifyConsumer(item, buyAmount);
            applyDefaultPromotion(item, buyAmount, discountHistory);
        }
    }

    public void checkWhetherNotifyConsumer(Item item, int buyAmount) {
        int promotionBundleAmount = item.getPromotion().get().getBundleAmount();
        int requiredBuyAmount = item.getPromotion().get().getBuyAmount();
        if (buyAmount > item.getPromotionQuantity()) {
            int outOfStockAmount =
                    buyAmount - (promotionBundleAmount * (item.getPromotionQuantity() / promotionBundleAmount));
            throw new OutOfPromotionStockException(outOfStockAmount);
        }
        if (buyAmount % promotionBundleAmount == requiredBuyAmount) {
            if ((buyAmount + promotionPolicy.getDefaultGiftAmount()) <= item.getPromotionQuantity()) {
                throw new NotAddGiftException();
            }
        }
    }

    public void applyDefaultPromotion(Item item, int buyAmount, DiscountHistory discountHistory) {
        int giftAmount = promotionPolicy.calculateGiftAmount(item, buyAmount);
        discountHistory.addGift(item, giftAmount);
    }

    public void applyPromotionWithoutRegularItems(OutOfStockPromotionDto dto, Cart cart,
                                                  DiscountHistory discountHistory) {
        int updatedBuyAmount = dto.buyAmount() - dto.outOfStockAmount();
        int giftAmount = promotionPolicy.calculateGiftAmount(dto.item(), updatedBuyAmount);
        discountHistory.addGift(dto.item(), giftAmount);
        cart.deductBuyAmountOf(dto.item(), dto.outOfStockAmount());
    }

    public void applyPromotionAddingGift(GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        int updatedBuyAmount = dto.buyAmount() + promotionPolicy.getDefaultGiftAmount();
        int giftAmount = promotionPolicy.calculateGiftAmount(dto.gift(), updatedBuyAmount);
        discountHistory.addGift(dto.gift(), giftAmount);
        cart.addBuyAmountOf(dto.gift(), promotionPolicy.getDefaultGiftAmount());
    }
}
