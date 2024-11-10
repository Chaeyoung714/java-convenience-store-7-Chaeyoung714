package store;


import store.controller.ConvenienceStoreController;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.service.PromotionService;
import store.service.PromotionServiceHandler;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        OrderService orderService = new OrderService(new MembershipPolicy());
        ConvenienceStoreController controller = new ConvenienceStoreController(
                new InputView(), new OutputView(), orderService, new PromotionServiceHandler(new PromotionService(new PromotionPolicy()))
        );
        controller.run();
    }
}
