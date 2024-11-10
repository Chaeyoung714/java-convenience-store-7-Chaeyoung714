package store.service;

import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;

public class PromotionServiceHandler {
    private final PromotionService promotionService;

    public PromotionServiceHandler(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public void applyPromotion(Cart consumerCart, DiscountHistory discountHistory) {
        try {
            promotionService.checkAndApplyPromotion(consumerCart, discountHistory);
        } catch (OutOfPromotionStockException | NotAddGiftException e) {
            throw e;
        }
    }

    public void orderAddingOrWithoutGift(String answer, GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            promotionService.applyPromotionAddingGift(dto, cart, discountHistory);
        }
        if (answer.equals("N")) {
            promotionService.applyDefaultPromotion(dto.gift(), dto.buyAmount(), discountHistory);
        }
    }

    public void orderWithOrWithoutRegularItems(String answer, OutOfStockPromotionDto dto, Cart cart,
                                               DiscountHistory discountHistory) {
        if (answer.equals("Y")) {
            promotionService.applyDefaultPromotion(dto.item(), dto.buyAmount(), discountHistory);
        }
        if (answer.equals("N")) {
            promotionService.applyPromotionWithoutRegularItems(dto, cart, discountHistory);
        }
    }
}
