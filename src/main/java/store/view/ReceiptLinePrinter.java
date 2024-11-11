package store.view;

import store.dto.PromotionHistoryDtos.PromotionHistoryDto;
import store.dto.PurchaseHistoryDtos.PurchaseHistoryDto;

public class ReceiptLinePrinter {
    private static final String DIVISION_LINE = "=";
    private static final int TOTAL_BILL_WIDTH = 35;
    private static final int PRODUCT_NAME_WIDTH = 19;
    private static final int BUY_AMOUNT_WIDTH = 9;
    private static final int PRICE_WIDTH = 7;
    private static StringBuilder lineBuilder = new StringBuilder();

    public void printDivisionLine(String title) {
        StringBuilder startLine = new StringBuilder(DIVISION_LINE.repeat(TOTAL_BILL_WIDTH));
        int halfIndexOfBillWidth = TOTAL_BILL_WIDTH / 2;
        int halfLengthOfName = title.length() / 2;
        startLine.replace(halfIndexOfBillWidth - halfLengthOfName, halfIndexOfBillWidth + halfLengthOfName, title);
        System.out.println(startLine);
    }

    public String printPurchaseHistoryStartLine() {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "상품명"));
        lineBuilder.append(String.format("%-" + BUY_AMOUNT_WIDTH + "s", "수량"));
        lineBuilder.append(String.format("%-" + PRICE_WIDTH + "s", "금액"));
        return lineBuilder.toString();
    }

    public String printPurchaseHistoryLine(PurchaseHistoryDto dto) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", dto.name()));
        lineBuilder.append(String.format("%-," + BUY_AMOUNT_WIDTH + "d", dto.buyAmount()));
        lineBuilder.append(String.format("%," + PRICE_WIDTH + "d", dto.totalCost()));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String printPromotionHistoryLine(PromotionHistoryDto dto) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", dto.name()));
        lineBuilder.append(
                String.format("%-," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", dto.amount()));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String printTotalProductPrice(int totalItemCost, int totalBuyAmount) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "총구매액"));
        lineBuilder.append(String.format("%-" + BUY_AMOUNT_WIDTH + "d", totalBuyAmount));
        lineBuilder.append(String.format("%," + PRICE_WIDTH + "d", totalItemCost));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String printPromotionDiscountAmount(int promotionDiscountAmount) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "행사할인"));
        lineBuilder.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", -promotionDiscountAmount));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String printMembershipDiscoutAmount(int membershipDiscountAmount) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "멤버십할인"));
        lineBuilder.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", -membershipDiscountAmount));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String printFinalCost(int finalCost) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format("%-" + PRODUCT_NAME_WIDTH + "s", "내실돈"));
        lineBuilder.append(
                String.format("%," + (BUY_AMOUNT_WIDTH + PRICE_WIDTH) + "d", finalCost));
        return lineBuilder.toString();
    }
}
