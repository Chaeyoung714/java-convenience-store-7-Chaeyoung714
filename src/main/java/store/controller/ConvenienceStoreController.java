package store.controller;

import store.model.Cart;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceStoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printProducts();
        String purchasingItems = inputView.readPurchasingItems();
        Cart cart = Cart.of(purchasingItems);

    }
}
