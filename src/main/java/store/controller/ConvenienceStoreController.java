package store.controller;

import static store.util.Answer.YES;

import store.dto.output.ItemStockDtos;
import store.dto.output.ReceiptDto;
import store.model.consumer.Cart;
import store.model.consumer.DiscountHistory;
import store.model.consumer.PurchaseCost;
import store.model.item.Items;
import store.model.item.ItemsFactory;
import store.model.promotion.Promotions;
import store.service.MembershipService;
import store.service.handlerWithController.PromotionServiceOutboundHandler;
import store.service.OrderService;
import store.util.FileScanner;
import store.view.input.OrderInputView;
import store.view.output.ItemStockOutputView;
import store.view.output.ReceiptOutputView;

public class ConvenienceStoreController {
    private final OrderInputView orderInputView;
    private final ReceiptOutputView receiptOutputView;
    private final ItemStockOutputView itemStockOutputView;
    private final OrderService orderService;
    private final MembershipService membershipService;
    private final PromotionServiceOutboundHandler promotionServiceHandler;

    public ConvenienceStoreController(OrderInputView orderInputView, ReceiptOutputView receiptOutputView,
                                      ItemStockOutputView itemStockOutputView, OrderService orderService,
                                      MembershipService membershipService,
                                      PromotionServiceOutboundHandler promotionServiceHandler) {
        this.orderInputView = orderInputView;
        this.receiptOutputView = receiptOutputView;
        this.itemStockOutputView = itemStockOutputView;
        this.orderService = orderService;
        this.membershipService = membershipService;
        this.promotionServiceHandler = promotionServiceHandler;
    }

    public void run() {
        Items items = registerConvenienceStore();
        purchaseOnce(items);
    }

    private Items registerConvenienceStore() {
        try {
            Promotions promotions = Promotions.of(FileScanner.readFile("./src/main/resources/promotions.md"));
            return ItemsFactory.of(FileScanner.readFile("./src/main/resources/products.md"), promotions);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("[SYSTEM] 매장 내 오류로 서비스 이용이 일시 중단되었습니다. 이용에 불편을 드려 죄송합니다.");
        }
    }

    private void purchaseOnce(Items items) {
        showItemStock(items);
        DiscountHistory discountHistory = new DiscountHistory();
        Cart cart = orderItems(items, discountHistory);
        applyMemberShip(cart, discountHistory);
        showPurchaseResult(cart, discountHistory);
        restartOrEndPurchase(items);
    }

    private void showItemStock(Items items) {
        itemStockOutputView.printItemsStock(ItemStockDtos.of(items));
    }

    private Cart orderItems(Items items, DiscountHistory discountHistory) {
        while (true) {
            try {
                String orderDetails = orderInputView.readPurchasingItems();
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
                String answer = orderInputView.readApplyMemberShip();
                membershipService.applyMemberShip(answer, cart, discountHistory);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showPurchaseResult(Cart cart, DiscountHistory discountHistory) {
        PurchaseCost purchaseCost = orderService.calculateCost(cart, discountHistory);
        receiptOutputView.printReceipt(new ReceiptDto(cart, discountHistory, purchaseCost));
    }

    private void restartOrEndPurchase(Items items) {
        while (true) {
            try {
                String answer = orderInputView.readRestartPurchase();
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
