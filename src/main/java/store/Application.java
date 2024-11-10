package store;


import store.controller.ConvenienceStoreController;
import store.controller.Observer;
import store.controller.PromotionController;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.service.MembershipService;
import store.service.PromotionService;
import store.service.PromotionServiceInboundHandler;
import store.service.PromotionServiceOutboundHandler;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();

        PromotionService promotionService = new PromotionService(new PromotionPolicy());
        PromotionServiceInboundHandler promotionServiceInboundHandler = new PromotionServiceInboundHandler(
                promotionService);
        Observer promotionController = new PromotionController(inputView, promotionServiceInboundHandler);
        PromotionServiceOutboundHandler promotionServiceOutboundHandler = new PromotionServiceOutboundHandler(
                promotionService, promotionController);

        MembershipService membershipService = new MembershipService(new MembershipPolicy());

        ConvenienceStoreController controller = new ConvenienceStoreController(
                inputView
                , new OutputView()
                , new OrderService()
                , membershipService
                , promotionServiceOutboundHandler);
        controller.run();
    }
}
