package store.view;

import java.util.List;
import java.util.Map;
import store.Cart;
import store.Customer;
import store.Product;
import store.Products;

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

}
