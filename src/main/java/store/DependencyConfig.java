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
import store.view.InputView;
import store.view.OutputView;

public class DependencyConfig {

    public InputView inputView() {
        return new InputView();
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
                inputView()
                , promotionServiceInboundHandler());
    }

    public MembershipService membershipService() {
        return new MembershipService(new MembershipPolicy());
    }

    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController(
                inputView()
                , new OutputView()
                , new OrderService()
                , membershipService()
                , promotionServiceOutboundHandler()
        );
    }
}
