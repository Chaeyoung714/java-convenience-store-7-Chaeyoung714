package store.view;

import store.dto.CostResultDto;
import store.dto.ItemStockDtos;
import store.dto.ItemStockDtos.ItemStockDto;
import store.dto.PromotionHistoryDtos;
import store.dto.PromotionHistoryDtos.PromotionHistoryDto;
import store.dto.PurchaseHistoryDtos;
import store.dto.PurchaseHistoryDtos.PurchaseHistoryDto;
import store.dto.ReceiptDto;

public class OutputView {
    private final ReceiptLinePrinter receiptLinePrinter;

    public OutputView(ReceiptLinePrinter receiptLinePrinter) {
        this.receiptLinePrinter = receiptLinePrinter;
    }

    public void printItemsStock(ItemStockDtos dtos) {
        printItemStockStartLine();
        for (ItemStockDto dto : dtos.dtos()) {
            String regularQuantity = quantityForPrint(dto.regularQuantity());
            String promotionQuantity = quantityForPrint(dto.promotionQuantity());
            if (!dto.promotionName().isEmpty()) {
                System.out.println(String.format(
                        "- %s %,d원 %s %s"
                        , dto.name(), dto.price(), promotionQuantity, dto.promotionName()));
            }
            System.out.println(String.format("- %s %,d원 %s"
                    , dto.name(), dto.price(), regularQuantity));
        }
    }

    private void printItemStockStartLine() {
        System.out.println(System.lineSeparator() + "안녕하세요. W편의점입니다."
                + System.lineSeparator() + "현재 보유하고 있는 상품입니다."
                + System.lineSeparator());
    }

    private String quantityForPrint(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.format("%,d개", quantity);
    }

    public void printReceipt(ReceiptDto dto) {
        System.out.println();
        receiptLinePrinter.printDivisionLine("W 편의점");
        printPurchaseHistories(dto.getPurchaseHistoryDtos());
        receiptLinePrinter.printDivisionLine("증\t정");
        printPromotionHistories(dto.getPromotionHistoryDtos());
        receiptLinePrinter.printDivisionLine("===");
        printCostResult(dto.getCostResultDto());
    }

    private void printPurchaseHistories(PurchaseHistoryDtos dtos) {
        System.out.println(receiptLinePrinter.printPurchaseHistoryStartLine());
        StringBuilder purchaseHistory = new StringBuilder();
        for (PurchaseHistoryDto dto : dtos.dtos()) {
            purchaseHistory.append(receiptLinePrinter.printPurchaseHistoryLine(dto));
        }
        System.out.print(purchaseHistory);
    }

    private void printPromotionHistories(PromotionHistoryDtos dtos) {
        StringBuilder promotionHistory = new StringBuilder();
        for (PromotionHistoryDto dto : dtos.dtos()) {
            promotionHistory.append(receiptLinePrinter.printPromotionHistoryLine(dto));
        }
        System.out.print(promotionHistory);
    }

    private void printCostResult(CostResultDto dto) {
        StringBuilder costResults = new StringBuilder();
        costResults.append(receiptLinePrinter.printTotalProductPrice(dto.totalItemCost(), dto.totalBuyAmount()));
        costResults.append(receiptLinePrinter.printPromotionDiscountAmount(dto.promotionDiscountAmount()));
        costResults.append(receiptLinePrinter.printMembershipDiscoutAmount(dto.membershipDiscountAmount()));
        costResults.append(receiptLinePrinter.printFinalCost(dto.finalCost()));
        System.out.println(costResults);
    }


}
