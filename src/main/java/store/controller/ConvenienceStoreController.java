package store.controller;

import static store.util.Answer.YES;

import store.dto.ItemStockDtos;
import store.dto.ReceiptDto;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.consumer.PurchaseCost;
import store.model.item.Items;
import store.service.MembershipService;
import store.service.handlerWithController.PromotionServiceOutboundHandler;
import store.service.OrderService;
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

    public void run(Items items) {
        purchaseOnce(items);
    }

    private void purchaseOnce(Items items) {
        outputView.printItemsStock(ItemStockDtos.of(items));
        DiscountHistory discountHistory = new DiscountHistory();
        Cart cart = orderItems(items, discountHistory);
        applyMemberShip(cart, discountHistory);
        showPurchaseResult(cart, discountHistory);
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

    private void showPurchaseResult(Cart cart, DiscountHistory discountHistory) {
        PurchaseCost purchaseCost = orderService.calculateCost(cart, discountHistory);
        outputView.printReceipt(new ReceiptDto(cart, discountHistory, purchaseCost));
    }

    private void restartOrEndPurchase(Items items) {
        while (true) {
            try {
                String answer = inputView.readRestartPurchase();
                if (answer.equals(YES.getFormat())) {
                    purchaseOnce(items);
                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
