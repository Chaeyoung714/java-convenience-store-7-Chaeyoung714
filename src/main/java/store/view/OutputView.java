package store.view;

import java.util.List;
import java.util.Map;
import store.Cart;
import store.Customer;
import store.Product;

public class OutputView {
    private static final int TOTAL_BILL_WIDTH = 35;
    private static final int PRODUCT_NAME_WIDTH = 19;
    private static final int BUY_AMOUNT_WIDTH = 9;
    private static final int PRICE_WIDTH = 7;
    private static StringBuilder partBuilder = new StringBuilder();

    public void printProudcts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다."
                + System.lineSeparator()
                + "현재 보유하고 있는 상품입니다."
                + System.lineSeparator()
        );

        for (Product product : products) {
            String name = product.getName();
            int price = product.getPrice();
            String promotionQuantity = quantityForPrint(product.getPromotionQuantity());
            String regularQuantity = quantityForPrint(product.getRegularQuantity());
            if (product.hasOngoingPromotion()) {
                String promotionName = product.getPromotion().get().getName();
                System.out.printf("- %s %,d원 %s %s" + System.lineSeparator()
                        , name, price, promotionQuantity, promotionName);
            }
            System.out.printf("- %s %,d원 %s" + System.lineSeparator()
                    , name, price, regularQuantity);
        }
        System.out.println();
    }

    private String quantityForPrint(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.format("%d개", quantity);
    }

    public void printReceipt(Customer customer) {
        Cart cart = customer.getCart();
        printDivisionLine("W 편의점");
        printSecondLine();
        printPurchaseHistory(cart.getCart());
        printDivisionLine("증\t정");
        printPromotionHistory(customer.getPromotionGetProducts());
        printDivisionLine("=====");
        printPriceResult(customer);
    }

    private void printDivisionLine(String title) {
        StringBuilder startLine = new StringBuilder("=".repeat(TOTAL_BILL_WIDTH));
        int halfIndexOfBillWidth = TOTAL_BILL_WIDTH / 2;
        int halfLengthOfName = title.length() / 2;
        startLine.replace(halfIndexOfBillWidth - halfLengthOfName, halfIndexOfBillWidth + halfLengthOfName, title);
        System.out.println(startLine);
    }

    private void printSecondLine() {
        StringBuilder secondLine = new StringBuilder();
        secondLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "상품명"));
        secondLine.append(String.format("%-" + BUY_AMOUNT_WIDTH + "s", "수량"));
        secondLine.append(String.format("%-" + PRICE_WIDTH + "s", "금액"));
        System.out.println(secondLine);
    }

    private void printPurchaseHistory(Map<Product, Integer> cart) {
        StringBuilder purchaseHistoryLine = new StringBuilder();
        for (Product product : cart.keySet()) {
            int buyAmount = cart.get(product);
            purchaseHistoryLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", product.getName()));
            purchaseHistoryLine.append(String.format("%-" + BUY_AMOUNT_WIDTH + "s", buyAmount));
            purchaseHistoryLine.append(String.format("%" + PRICE_WIDTH + "s", product.getPrice() * buyAmount));
            purchaseHistoryLine.append(System.lineSeparator());
        }
        System.out.println(purchaseHistoryLine);
    }

    private void printPromotionHistory(Map<Product, Integer> promotionGetProducts) {
        StringBuilder promotionHistoryLine = new StringBuilder();
        for (Product product : promotionGetProducts.keySet()) {
            promotionHistoryLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", product.getName()));
            promotionHistoryLine.append(
                    String.format("%-" + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "s", promotionGetProducts.get(product)));
        }
        System.out.println(promotionHistoryLine);
    }

    private void printPriceResult(Customer customer) {
        StringBuilder totalProductLine = new StringBuilder();
        int totalProductPrice = customer.getCart().getTotalProductPrice();
        int totalProductAmount = customer.getCart().getTotalProductAmount();
        totalProductLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "총구매액"));
        totalProductLine.append(String.format("%-" + BUY_AMOUNT_WIDTH + "d", totalProductAmount));
        totalProductLine.append(String.format("%," + PRICE_WIDTH + "d", totalProductPrice));

        StringBuilder promotionLine = new StringBuilder();
        promotionLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "행사할인"));
        promotionLine.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", -customer.getPromotionDiscountAmount()));

        StringBuilder membershipLine = new StringBuilder();
        membershipLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "멤버십할인"));
        membershipLine.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", -customer.getMembershipDiscountAmount()));

        StringBuilder paymentLine = new StringBuilder();
        paymentLine.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "내실돈"));
        paymentLine.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", customer.getPaymentAmount()));

        System.out.println(totalProductLine);
        System.out.println(promotionLine);
        System.out.println(membershipLine);
        System.out.println(paymentLine);
    }
}
