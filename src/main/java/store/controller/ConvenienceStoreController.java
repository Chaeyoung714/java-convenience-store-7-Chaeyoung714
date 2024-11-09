package store.controller;

import store.Promotion;
import store.discountPolicy.PromotionPolicy;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.Item;
import store.model.Items;
import store.model.Promotions;
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
        Promotions promotions = Promotions.register();
        Items items = Items.register(promotions);
        outputView.printProducts(items);
        String purchasingItems = inputView.readPurchasingItems();
        Cart cart = Cart.of(purchasingItems, items);
        orderService.checkStock(cart);
        PromotionPolicy promotionPolicy = new PromotionPolicy();
        applyPromotion(promotionPolicy, cart);
        orderService.orderItems(cart);

    }

    private void applyPromotion(PromotionPolicy promotionPolicy, Cart cart) {
        try {
            orderService.applyPromotion(promotionPolicy, cart);
        } catch (OutOfPromotionStockException e) {
            checkOrderIncludingRegularItems(promotionPolicy, e.getItem(), e.getBuyAmount(), cart);
        } catch (DidNotBringPromotionGiveProductException e) {
            checkAddGift(promotionPolicy, e.getGift(), e.getBuyAmount(), cart);
        }
    }

    private void checkOrderIncludingRegularItems(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        while (true) {
            try {
                String answer = inputView.readOutOfStockPromotion(item, buyAmount);
                if (answer.equals("Y")) {
                    orderService.orderIncludingRegularItems(promotionPolicy, item, buyAmount);
                }
                if (answer.equals("N")) {
                    orderService.orderExcludingRegularItems(promotionPolicy, item, buyAmount, cart);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkAddGift(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        while (true) {
            try {
                String answer = inputView.readAddGift(item);
                if (answer.equals("Y")) {
                    orderService.orderAddingGift(promotionPolicy, item, buyAmount, cart);
                }
                if (answer.equals("N")) {
                    orderService.orderExcludingGift(promotionPolicy, item, buyAmount);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
