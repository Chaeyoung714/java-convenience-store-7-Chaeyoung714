package store.controller;

import store.discountPolicy.PromotionPolicy;
import store.model.Cart;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, OrderService orderService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
    }

    public void run() {
        outputView.printProducts();
        String purchasingItems = inputView.readPurchasingItems();
        Cart cart = Cart.of(purchasingItems);
        orderService.checkStock(cart);
        PromotionPolicy promotionPolicy = new PromotionPolicy();

    }
}
