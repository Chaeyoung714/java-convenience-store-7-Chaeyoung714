package store.view.output;

import static store.view.output.ReceiptFormatter.PROMOTION_ITEM_STOCK;
import static store.view.output.ReceiptFormatter.REGULAR_ITEM_STOCK;

import store.dto.output.ItemStockDtos;
import store.dto.output.ItemStockDtos.ItemStockDto;

public class ItemStockOutputView {

    public void printItemsStock(ItemStockDtos dtos) {
        printItemStockStartLine();
        for (ItemStockDto dto : dtos.dtos()) {
            if (!dto.promotionName().isEmpty()) {
                String promotionQuantity = quantityForPrint(dto.promotionQuantity());
                System.out.println(String.format(PROMOTION_ITEM_STOCK
                        , dto.name(), dto.price(), promotionQuantity, dto.promotionName()));
            }
            String regularQuantity = quantityForPrint(dto.regularQuantity());
            System.out.println(String.format(REGULAR_ITEM_STOCK
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
}
