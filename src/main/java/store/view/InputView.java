package store.view;


import camp.nextstep.edu.missionutils.Console;
import store.Product;

public class InputView {
    public String readPurchasingProducts() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String purchasingProducts = Console.readLine();
        validateInputPurchasingProducts(purchasingProducts);
        return purchasingProducts;
    }

    public void validateInputPurchasingProducts(String purchasingProducts) {
        if (purchasingProducts == null || purchasingProducts.isBlank()
                || !purchasingProducts.matches("\\[[^\\s\\]]+-[^\\s\\]]+\\](,\\[[^\\s\\]]+-[^\\s\\]]+\\])*")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public String readIfBringPromotionGetProduct(String promotionGetProductName) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
                , promotionGetProductName);
        String answer = Console.readLine();
        validateInputYesOrNo(answer);
        return answer;
    }

    public String readIfBuyOutOfStockPromotionProduct(Product product, int outOfStockAmount) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
                , product.getName(), outOfStockAmount);
        String answer = Console.readLine();
        validateInputYesOrNo(answer);
        return answer;
    }

    public void validateInputYesOrNo(String answer) {
        if (!answer.equals("Y") || !answer.equals("N")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
