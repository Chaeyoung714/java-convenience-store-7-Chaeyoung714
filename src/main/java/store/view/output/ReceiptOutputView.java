package store.view.output;

import store.dto.output.CostResultDto;
import store.dto.output.PromotionHistoryDtos;
import store.dto.output.PromotionHistoryDtos.PromotionHistoryDto;
import store.dto.output.PurchaseHistoryDtos;
import store.dto.output.PurchaseHistoryDtos.PurchaseHistoryDto;
import store.dto.output.ReceiptDto;

public class ReceiptOutputView {
    private final ReceiptLinePrinter receiptLinePrinter;

    public ReceiptOutputView(ReceiptLinePrinter receiptLinePrinter) {
        this.receiptLinePrinter = receiptLinePrinter;
    }

    public void printReceipt(ReceiptDto dto) {
        System.out.println();
        receiptLinePrinter.printDivisionLineWith("W 편의점");
        printPurchaseHistories(dto.getPurchaseHistoryDtos());
        receiptLinePrinter.printDivisionLineWith("증\t정");
        printPromotionHistories(dto.getPromotionHistoryDtos());
        receiptLinePrinter.printDivisionLine();
        printCostResult(dto.getCostResultDto());
    }

    private void printPurchaseHistories(PurchaseHistoryDtos dtos) {
        System.out.println(receiptLinePrinter.printPurchaseHistoryStartLine());
        StringBuilder purchaseHistory = new StringBuilder();
        for (PurchaseHistoryDto dto : dtos.dtos()) {
            purchaseHistory.append(receiptLinePrinter.buildPurchaseHistoryLine(dto));
        }
        System.out.print(purchaseHistory);
    }

    private void printPromotionHistories(PromotionHistoryDtos dtos) {
        StringBuilder promotionHistory = new StringBuilder();
        for (PromotionHistoryDto dto : dtos.dtos()) {
            promotionHistory.append(receiptLinePrinter.buildPromotionHistoryLine(dto));
        }
        System.out.print(promotionHistory);
    }

    private void printCostResult(CostResultDto dto) {
        StringBuilder costResults = new StringBuilder();
        costResults.append(receiptLinePrinter.buildTotalProductPriceLine(dto.totalItemCost(), dto.totalBuyAmount()));
        costResults.append(receiptLinePrinter.buildPromotionDiscountAmountLine(dto.promotionDiscountAmount()));
        costResults.append(receiptLinePrinter.buildMembershipDiscountAmountLine(dto.membershipDiscountAmount()));
        costResults.append(receiptLinePrinter.buildFinalCostLine(dto.finalCost()));
        System.out.println(costResults);
    }
}
