package store.view;

import java.util.Map;
import store.discountPolicy.MembershipPolicy;
import store.discountPolicy.PromotionPolicy;
import store.model.Cart;
import store.model.Item;
import store.model.Items;

public class OutputView {
    private static final int TOTAL_BILL_WIDTH = 35;
    private static final int PRODUCT_NAME_WIDTH = 19;
    private static final int BUY_AMOUNT_WIDTH = 9;
    private static final int PRICE_WIDTH = 7;
    private static StringBuilder partBuilder = new StringBuilder();

    public void printProducts(Items items) {
        System.out.println("안녕하세요. W편의점입니다."
                + System.lineSeparator()
                + "현재 보유하고 있는 상품입니다."
                + System.lineSeparator());

        for (Item item : items.getItems()) {
            String name = item.getName();
            int price = item.getPrice();
            String regularQuantity = quantityForPrint(item.getRegularQuantity());
            String promotionQuantity = quantityForPrint(item.getPromotionQuantity());

            if (item.hasOngoingPromotion()) {
                String promotionName = item.getPromotion().get().getName();
                System.out.println(String.format(
                        "- %s %,d원 %s %s"
                        , name, price, promotionQuantity, promotionName));
            }
            System.out.println(String.format("- %s %,d원 %s"
                    , name, price, regularQuantity));
        }
    }

    private String quantityForPrint(int quantity) {
        if (quantity == 0) {
            return "재고 없음";

        }
        return String.format("%,d개", quantity);
    }

    public void printReceipt(Cart cart, PromotionPolicy promotionPolicy, MembershipPolicy membershipPolicy) {
        printDivisionLine("W 편의점");
        printPurchaseHistoryStartLine();
        printPurchaseHistory(cart.getCart());
        printDivisionLine("증\t정");
        printPromotionHistory(promotionPolicy.getGift());
        printDivisionLine("===");
        printCostResult(cart, promotionPolicy, membershipPolicy);
    }

    private void printDivisionLine(String title) {
        StringBuilder startLine = new StringBuilder("=".repeat(TOTAL_BILL_WIDTH));
        int halfIndexOfBillWidth = TOTAL_BILL_WIDTH / 2;
        int halfLengthOfName = title.length() / 2;
        startLine.replace(halfIndexOfBillWidth - halfLengthOfName, halfIndexOfBillWidth + halfLengthOfName, title);
        System.out.println(startLine);
    }

    private void printPurchaseHistoryStartLine() {
        StringBuilder secondLine = new StringBuilder();
        secondLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "상품명"));
        secondLine.append(String.format("%-" + BUY_AMOUNT_WIDTH + "s", "수량"));
        secondLine.append(String.format("%-" + PRICE_WIDTH + "s", "금액"));
        System.out.println(secondLine);
    }

    private void printPurchaseHistory(Map<Item, Integer> cart) {
        StringBuilder purchaseHistory = new StringBuilder();
        for (Item item : cart.keySet()) {
            int buyAmount = cart.get(item);
            purchaseHistory.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", item.getName()));
            purchaseHistory.append(String.format("%-" + BUY_AMOUNT_WIDTH + "s", buyAmount));
            purchaseHistory.append(String.format("%" + PRICE_WIDTH + "s", item.getPrice() * buyAmount));
            purchaseHistory.append(System.lineSeparator());
        }
        System.out.println(purchaseHistory);
    }

    private void printPromotionHistory(Map<Item, Integer> gifts) {
        StringBuilder promotionHistory = new StringBuilder();
        for (Item item : gifts.keySet()) {
            promotionHistory.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", item.getName()));
            promotionHistory.append(
                    String.format("%-" + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "s", gifts.get(item)));
        }
        System.out.println(promotionHistory);
    }

    private void printCostResult(Cart cart, PromotionPolicy promotionPolicy, MembershipPolicy membershipPolicy) {
        printTotalProductPrice(cart);
        printPromotionDiscountAmount(promotionPolicy);
        printMembershipDiscoutAmount(membershipPolicy);
        printFinalCost(cart, promotionPolicy, membershipPolicy);
    }

    private void printTotalProductPrice(Cart cart) {
        StringBuilder totalProductPrice = new StringBuilder();
        totalProductPrice.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "총구매액"));
        totalProductPrice.append(String.format("%-" + BUY_AMOUNT_WIDTH + "d", cart.getTotalBuyAmount()));
        totalProductPrice.append(String.format("%," + PRICE_WIDTH + "d", cart.getTotalCost()));
        System.out.println(totalProductPrice);
    }

    private void printPromotionDiscountAmount(PromotionPolicy promotionPolicy) {
        StringBuilder promotionLine = new StringBuilder();
        promotionLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "행사할인"));
        promotionLine.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", -promotionPolicy.getDiscountAmount()));
    }

    private void printMembershipDiscoutAmount(MembershipPolicy membershipPolicy) {
        StringBuilder membershipLine = new StringBuilder();
        membershipLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "멤버십할인"));
        membershipLine.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", -membershipPolicy.getDiscountAmount()));

    }

    private void printFinalCost(Cart cart, PromotionPolicy promotionPolicy, MembershipPolicy membershipPolicy) {
        int finalCost =
                cart.getTotalCost() - (promotionPolicy.getDiscountAmount() + membershipPolicy.getDiscountAmount());
        StringBuilder paymentLine = new StringBuilder();
        paymentLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "내실돈"));
        paymentLine.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", finalCost));
    }
}
