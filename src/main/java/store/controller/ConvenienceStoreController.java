package store.controller;

import store.discountPolicy.PromotionPolicy;
import store.model.Cart;
import store.service.OrderSevice;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderSevice orderSevice;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, OrderSevice orderSevice) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderSevice = orderSevice;
    }

    public void run() {
        outputView.printProducts();
        String purchasingItems = inputView.readPurchasingItems();
        Cart cart = Cart.of(purchasingItems);
        orderSevice.checkStock(cart);
        PromotionPolicy promotionPolicy = new PromotionPolicy();

    }
}
