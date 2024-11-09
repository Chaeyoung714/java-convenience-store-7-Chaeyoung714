package store.view;

public class InputValidator {
    public static void validatePurchasingItems(String purchasingItems) {
        if (!purchasingItems.matches("\\[[^\\s\\]]+-[^\\s\\]]+\\](,\\[[^\\s\\]]+-[^\\s\\]]+\\])*")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static void validateYesOrNoAnswer(String answer) {
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
