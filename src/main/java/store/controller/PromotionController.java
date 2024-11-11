package store.controller;

import store.dto.promotion.GiftDto;
import store.dto.promotion.OutOfStockPromotionDto;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.service.handlerWithController.PromotionServiceInboundHandler;
import store.view.input.PromotionInputView;

public class PromotionController implements PromotionServiceObserver {
    private final PromotionInputView promotionInputView;
    private final PromotionServiceInboundHandler promotionServiceHandler;

    public PromotionController(PromotionInputView promotionInputView,
                               PromotionServiceInboundHandler promotionServiceHandler) {
        this.promotionInputView = promotionInputView;
        this.promotionServiceHandler = promotionServiceHandler;
    }

    @Override
    public void notifyOutOfPromotionStock(OutOfStockPromotionDto dto, Cart cart, DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = promotionInputView.readOutOfStockPromotion(dto);
                promotionServiceHandler.orderWithOrWithoutRegularItems(answer, dto, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void notifyAddGift(GiftDto dto, Cart cart, DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = promotionInputView.readAddGift(dto);
                promotionServiceHandler.orderAddingOrWithoutGift(answer, dto, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
