package store.view;

import java.util.List;
import store.Product;

public class OutputView {
    public void printProudcts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다."
                + System.lineSeparator()
                + "현재 보유하고 있는 상품입니다."
                + System.lineSeparator()
        );

        for (Product product : products) {
            String name = product.getName();
            int price = product.getPrice();
            String quantity = quantityForPrint(product.getQuantity());
            String promotionName = product.getPromotion().getName();
            System.out.printf("- %s %,d원 %d개 %s" + System.lineSeparator()
                    , name, price, quantity, promotionName);
        }
        System.out.println();
    }

    private String quantityForPrint(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.valueOf(quantity);
    }
}