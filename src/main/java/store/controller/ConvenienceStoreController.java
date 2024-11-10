package store.controller;

import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Item;
import store.model.Items;
import store.model.Promotions;
import store.service.OrderService;
import store.util.FileScanner;
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
        Promotions promotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        Items items = Items.register(FileScanner.readFile("./src/main/resources/products.md"), promotions);

        purchaseOnce(items);
    }

    private void purchaseOnce(Items items) {
        outputView.printProducts(items);
        DiscountHistory discountHistory = new DiscountHistory();
        Cart cart = orderItems(items, discountHistory);
        applyMemberShip(cart, discountHistory);
        outputView.printReceipt(cart, discountHistory);
        restartOrEndPurchase(items);
    }

    private Cart orderItems(Items items, DiscountHistory discountHistory) {
        while (true) {
            try {
                String purchasingItems = inputView.readPurchasingItems();
                Cart cart = Cart.of(purchasingItems, items);
                orderService.checkStock(cart);
                applyPromotion(cart, discountHistory);
                orderService.orderItems(cart);
                return cart;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void applyPromotion(Cart cart, DiscountHistory discountHistory) {
        try {
            orderService.applyPromotion(cart, discountHistory);
        } catch (OutOfPromotionStockException e) {
            checkOrderIncludingRegularItems(e.getItem(), e.getBuyAmount(), e.getOutOfStockAmount(), cart, discountHistory);
        } catch (DidNotBringPromotionGiveProductException e) {
            checkAddGift(e.getGift(), e.getBuyAmount(), cart, discountHistory);
        }
    }

    private void checkOrderIncludingRegularItems(Item item, int buyAmount, int outOfStockAmount, Cart cart,
                                                 DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = inputView.readOutOfStockPromotion(item, outOfStockAmount);
                orderService.orderWithOrWithoutRegularItems(answer, item, buyAmount, outOfStockAmount, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkAddGift(Item item, int buyAmount, Cart cart, DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = inputView.readAddGift(item);
                orderService.orderAddingOrWithoutGift(answer, item, buyAmount, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void applyMemberShip(Cart cart, DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = inputView.readApplyMemberShip();
                orderService.applyMemberShip(answer, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void restartOrEndPurchase(Items items) {
        while (true) {
            try {
                String answer = inputView.readRestartPurchase();
                if (answer.equals("Y")) {
                    purchaseOnce(items);
                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
