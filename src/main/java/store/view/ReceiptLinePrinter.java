package store.view;

import static store.view.ReceiptFormatter.*;

import store.dto.PromotionHistoryDtos.PromotionHistoryDto;
import store.dto.PurchaseHistoryDtos.PurchaseHistoryDto;

public class ReceiptLinePrinter {
    private static final String DIVISION_LINE = "=";
    private static final int TOTAL_BILL_WIDTH = 35;
    private static StringBuilder lineBuilder = new StringBuilder();

    public void printDivisionLineWith(String title) {
        StringBuilder divisionLine = new StringBuilder(DIVISION_LINE.repeat(TOTAL_BILL_WIDTH));
        int halfIndexOfBillWidth = TOTAL_BILL_WIDTH / 2;
        int halfLengthOfName = title.length() / 2;
        divisionLine.replace(halfIndexOfBillWidth - halfLengthOfName, halfIndexOfBillWidth + halfLengthOfName, title);
        System.out.println(divisionLine);
    }

    public void printDivisionLine() {
        StringBuilder divisionLine = new StringBuilder(DIVISION_LINE.repeat(TOTAL_BILL_WIDTH + 3));
        System.out.println(divisionLine);
    }

    public String printPurchaseHistoryStartLine() {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, "상품명"));
        lineBuilder.append(String.format(RECEIPT_MIDDLE_PART_STRING, "수량"));
        lineBuilder.append(String.format(RECEIPT_RIGHT_PART_STRING_ALIGN_LEFT, "금액"));
        return lineBuilder.toString();
    }

    public String buildPurchaseHistoryLine(PurchaseHistoryDto dto) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, dto.name()));
        lineBuilder.append(String.format(RECEIPT_MIDDLE_PART_NUMBER, dto.buyAmount()));
        lineBuilder.append(String.format(RECEIPT_RIGHT_PART_NUMBER, dto.totalCost()));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String buildPromotionHistoryLine(PromotionHistoryDto dto) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, dto.name()));
        lineBuilder.append(
                String.format(RECEIPT_MIDDLE_PART_NUMBER, dto.amount())); //다시 체크
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String buildTotalProductPriceLine(int totalItemCost, int totalBuyAmount) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, "총구매액"));
        lineBuilder.append(String.format(RECEIPT_MIDDLE_PART_NUMBER, totalBuyAmount));
        lineBuilder.append(String.format(RECEIPT_RIGHT_PART_NUMBER, totalItemCost));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String buildPromotionDiscountAmountLine(int promotionDiscountAmount) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, "행사할인"));
        lineBuilder.append(String.format(RECEIPT_MIDDLE_PART_STRING, ""));
        lineBuilder.append(String.format(
                RECEIPT_RIGHT_PART_STRING_ALIGN_RIGHT, String.format(MINUS_NUMBER, promotionDiscountAmount)));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String buildMembershipDiscountAmountLine(int membershipDiscountAmount) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, "멤버십할인"));
        lineBuilder.append(String.format(RECEIPT_MIDDLE_PART_STRING, ""));
        lineBuilder.append(String.format(
                        RECEIPT_RIGHT_PART_STRING_ALIGN_RIGHT, String.format(MINUS_NUMBER, membershipDiscountAmount)));
        lineBuilder.append(System.lineSeparator());
        return lineBuilder.toString();
    }

    public String buildFinalCostLine(int finalCost) {
        lineBuilder.setLength(0);
        lineBuilder.append(String.format(RECEIPT_LEFT_PART, "내실돈"));
        lineBuilder.append(String.format(RECEIPT_MIDDLE_PART_STRING, ""));
        lineBuilder.append(
                String.format(RECEIPT_RIGHT_PART_NUMBER, finalCost));
        return lineBuilder.toString();
    }
}
