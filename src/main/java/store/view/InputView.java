package store.view;


import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public String readPurchasingProducts() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String purchasingProducts = Console.readLine();
        if (purchasingProducts == null || purchasingProducts.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        return purchasingProducts;
    }
}
