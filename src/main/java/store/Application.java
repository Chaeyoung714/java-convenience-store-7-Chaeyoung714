package store;


import store.controller.ConvenienceStoreController;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ConvenienceStoreController controller = new ConvenienceStoreController(
                new InputView(), new OutputView(), new OrderService()
        );
        controller.run();
    }
}
