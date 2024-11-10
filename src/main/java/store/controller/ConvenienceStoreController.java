package store.controller;

import store.model.Cart;
import store.model.DiscountHistory;
import store.model.Items;
import store.model.Promotions;
import store.service.MembershipService;
import store.service.PromotionServiceOutboundHandler;
import store.service.OrderService;
import store.util.FileScanner;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;
    private final MembershipService membershipService;
    private final PromotionServiceOutboundHandler promotionServiceHandler;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, OrderService orderService,
                                      MembershipService membershipService,
                                      PromotionServiceOutboundHandler promotionServiceHandler) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
        this.membershipService = membershipService;
        this.promotionServiceHandler = promotionServiceHandler;
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
                String orderDetails = inputView.readPurchasingItems();
                Cart cart = orderService.registerOrder(orderDetails, items);
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
        promotionServiceHandler.applyPromotion(cart, discountHistory);
    }

//    private void checkOrderIncludingRegularItems(OutOfStockPromotionDto dto, Cart cart,
//                                                 DiscountHistory discountHistory) {
//        while (true) {
//            try {
//                String answer = inputView.readOutOfStockPromotion(dto);
//                promotionServiceHandler.orderWithOrWithoutRegularItems(answer, dto, cart, discountHistory);
//                return;
//            } catch (IllegalArgumentException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }

//    private void checkAddGift(GiftDto dto, Cart cart, DiscountHistory discountHistory) {
//        while (true) {
//            try {
//                String answer = inputView.readAddGift(dto);
//                promotionServiceHandler.orderAddingOrWithoutGift(answer, dto, cart, discountHistory);
//                return;
//            } catch (IllegalArgumentException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }

    private void applyMemberShip(Cart cart, DiscountHistory discountHistory) {
        while (true) {
            try {
                String answer = inputView.readApplyMemberShip();
                membershipService.applyMemberShip(answer, cart, discountHistory);
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
