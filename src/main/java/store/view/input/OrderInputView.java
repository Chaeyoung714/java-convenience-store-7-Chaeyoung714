package store.view.input;

import camp.nextstep.edu.missionutils.Console;

public class OrderInputView {
    public String readPurchasingItems() {
        System.out.println(System.lineSeparator()
                + "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String purchasingItems = Console.readLine();
        InputValidator.validatePurchasingItems(purchasingItems);
        return purchasingItems;
    }

    public String readApplyMemberShip() {
        System.out.println(System.lineSeparator()
                + "멤버십 할인을 받으시겠습니까? (Y/N)");
        String answer = Console.readLine();
        InputValidator.validateYesOrNoAnswer(answer);
        return answer;
    }

    public String readRestartPurchase() {
        System.out.println(System.lineSeparator()
                + "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String answer = Console.readLine();
        InputValidator.validateYesOrNoAnswer(answer);
        return answer;
    }
}
