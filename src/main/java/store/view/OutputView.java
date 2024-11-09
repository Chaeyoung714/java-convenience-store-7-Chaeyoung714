package store.view;

import store.model.Product;
import store.model.Products;

public class OutputView {

    public void printProducts() {
        System.out.println("안녕하세요. W편의점입니다."
                + System.lineSeparator()
                + "현재 보유하고 있는 상품입니다."
                + System.lineSeparator());

        for (Product product : Products.getProducts()) {
            String name = product.getName();
            int price = product.getPrice();
            String regularQuantity = quantityForPrint(product.getRegularQuantity());
            String promotionQuantity = quantityForPrint(product.getPromotionQuantity());

            if (product.hasOngoingPromotion()) {
                String promotionName = product.getPromotion().get().getName();
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
