package store.service;

import java.util.Map;
import store.controller.PromotionServiceObserver;
import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Item;

public class PromotionServiceOutboundHandler {
    private final PromotionService promotionService;
    private final PromotionServiceObserver promotionServiceObserver;

    public PromotionServiceOutboundHandler(PromotionService promotionService, PromotionServiceObserver promotionServiceObserver) {
        this.promotionService = promotionService;
        this.promotionServiceObserver = promotionServiceObserver;
    }

    public void applyPromotion(Cart consumerCart, DiscountHistory discountHistory) {
        Map<Item, Integer> cart = consumerCart.getCart();
        for (Item item : cart.keySet()) {
            try {
                promotionService.checkAndApplyPromotion(item, cart.get(item), discountHistory);
            } catch (OutOfPromotionStockException e) {
                promotionServiceObserver.notifyOutOfPromotionStock(
                        OutOfStockPromotionDto.from(item, cart.get(item)), consumerCart, discountHistory);
            } catch (NotAddGiftException e) {
                promotionServiceObserver.notifyAddGift(new GiftDto(item, cart.get(item)), consumerCart, discountHistory);
            }
        }
    }
}
