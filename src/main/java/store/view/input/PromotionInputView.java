package store.view.input;

import camp.nextstep.edu.missionutils.Console;
import store.dto.promotion.GiftDto;
import store.dto.promotion.OutOfStockPromotionDto;

public class PromotionInputView {

    public String readOutOfStockPromotion(OutOfStockPromotionDto dto) {
        System.out.println(String.format(System.lineSeparator()
                        + "현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
                , dto.itemName(), dto.outOfStockAmount()));
        String answer = Console.readLine();
        InputValidator.validateYesOrNoAnswer(answer);
        return answer;
    }

    public String readAddGift(GiftDto dto) {
        System.out.println(String.format(System.lineSeparator()
                        + "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
                , dto.giftName()));
        String answer = Console.readLine();
        InputValidator.validateYesOrNoAnswer(answer);
        return answer;
    }
}
