package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.Item;

public class InputView {
    public String readPurchasingItems() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String purchasingItems = Console.readLine();
        InputValidator.validatePurchasingItems(purchasingItems);
        return purchasingItems;
    }

    public String readOutOfStockPromotion(Item item, int outOfStockAmount) {
        System.out.printf("현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
                , item.getName(), outOfStockAmount);
        String answer = Console.readLine();
        InputValidator.validateYesOrNoAnswer(answer);
        return answer;
    }
}
