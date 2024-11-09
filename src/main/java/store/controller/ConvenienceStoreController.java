package store.controller;

import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.model.Cart;
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
        PromotionPolicy promotionPolicy = new PromotionPolicy();
        MembershipPolicy membershipPolicy = new MembershipPolicy();

        outputView.printProducts(items);
        Cart cart = orderItems(items, promotionPolicy);
        applyMemberShip(promotionPolicy, membershipPolicy, cart);
        outputView.printReceipt(cart, promotionPolicy, membershipPolicy);
    }

    private Cart orderItems(Items items, PromotionPolicy promotionPolicy) {
        while (true) {
            try {
                String purchasingItems = inputView.readPurchasingItems();
                Cart cart = Cart.of(purchasingItems, items);
                orderService.checkStock(cart);
                applyPromotion(promotionPolicy, cart);
                orderService.orderItems(cart);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void applyPromotion(PromotionPolicy promotionPolicy, Cart cart) {
        try {
            orderService.applyPromotion(cart, promotionPolicy);
        } catch (OutOfPromotionStockException e) {
            checkOrderIncludingRegularItems(promotionPolicy, e.getItem(), e.getBuyAmount(), cart);
        } catch (DidNotBringPromotionGiveProductException e) {
            checkAddGift(promotionPolicy, e.getGift(), e.getBuyAmount(), cart);
        } finally {
            orderService.orderItems(cart);
        }
    }

    private void checkOrderIncludingRegularItems(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        while (true) {
            try {
                String answer = inputView.readOutOfStockPromotion(item, buyAmount);
                orderService.orderWithOrWithoutRegularItems(answer, promotionPolicy, item, buyAmount, cart);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkAddGift(PromotionPolicy promotionPolicy, Item item, int buyAmount, Cart cart) {
        while (true) {
            try {
                String answer = inputView.readAddGift(item);
                orderService.orderAddingOrWithoutGift(answer, promotionPolicy, item, buyAmount, cart);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void applyMemberShip(PromotionPolicy promotionPolicy, MembershipPolicy membershipPolicy, Cart cart) {
        while (true) {
            try {
                String answer = inputView.readApplyMemberShip();
                orderService.applyMemberShip(answer, promotionPolicy, membershipPolicy, cart);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
