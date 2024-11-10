package store.controller;

import store.dto.GiftDto;
import store.dto.OutOfStockPromotionDto;
import store.model.Cart;
import store.model.DiscountHistory;
import store.service.PromotionServiceInboundHandler;
import store.view.InputView;

public class PromotionController implements PromotionServiceObserver {
    private final InputView inputView;
    private final PromotionServiceInboundHandler promotionServiceHandler;

    public PromotionController(InputView inputView, PromotionServiceInboundHandler promotionServiceHandler) {
        this.inputView = inputView;
        this.promotionServiceHandler = promotionServiceHandler;
    }

    @Override
    public void notifyOutOfPromotionStock(OutOfStockPromotionDto dto, Cart cart, DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = inputView.readOutOfStockPromotion(dto);
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
                String answer = inputView.readAddGift(dto);
                promotionServiceHandler.orderAddingOrWithoutGift(answer, dto, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
