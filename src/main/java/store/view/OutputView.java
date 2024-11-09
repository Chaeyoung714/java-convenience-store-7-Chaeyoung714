package store.view;

import store.model.Item;
import store.model.Items;

public class OutputView {

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
}
