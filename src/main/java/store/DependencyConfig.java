package store;

import store.controller.ConvenienceStoreController;
import store.controller.PromotionController;
import store.controller.PromotionServiceObserver;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.service.MembershipService;
import store.service.OrderService;
import store.service.PromotionService;
import store.service.handlerWithController.PromotionServiceInboundHandler;
import store.service.handlerWithController.PromotionServiceOutboundHandler;
import store.view.input.OrderInputView;
import store.view.input.PromotionInputView;
import store.view.output.ItemStockOutputView;
import store.view.output.ReceiptOutputView;
import store.view.output.ReceiptLinePrinter;

public class DependencyConfig {

    public ReceiptOutputView receiptOutputView() {
        return new ReceiptOutputView(new ReceiptLinePrinter());
    }

    public PromotionService promotionService() {
        return new PromotionService(
                new PromotionPolicy());
    }

    public PromotionServiceInboundHandler promotionServiceInboundHandler() {
        return new PromotionServiceInboundHandler(
                promotionService());
    }

    public PromotionServiceOutboundHandler promotionServiceOutboundHandler() {
        return new PromotionServiceOutboundHandler(
                promotionService()
                , promotionServiceObserver());
    }

    public PromotionServiceObserver promotionServiceObserver() {
        return new PromotionController(
                new PromotionInputView()
                , promotionServiceInboundHandler());
    }

    public MembershipService membershipService() {
        return new MembershipService(new MembershipPolicy());
    }

    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController(
                new OrderInputView()
                , receiptOutputView()
                , new ItemStockOutputView()
                , new OrderService()
                , membershipService()
                , promotionServiceOutboundHandler()
        );
    }
}
