package store.service.handlerWithController;

import java.util.Map;
import store.controller.PromotionServiceObserver;
import store.dto.promotion.GiftDto;
import store.dto.promotion.OutOfStockPromotionDto;
import store.exceptions.NotAddGiftException;
import store.exceptions.OutOfPromotionStockException;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.item.Item;
import store.service.PromotionService;

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
                        new OutOfStockPromotionDto(item, cart.get(item), e.getOutOfStockAmount()), consumerCart, discountHistory);
            } catch (NotAddGiftException e) {
                promotionServiceObserver.notifyAddGift(
                        new GiftDto(item, cart.get(item)), consumerCart, discountHistory);
            }
        }
    }
}
