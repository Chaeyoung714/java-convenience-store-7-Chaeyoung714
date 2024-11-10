package store.view;

public class InputValidator {
    private static final String DELIMITERS_IN_ORDER_REGEX_WITH_BLANK_UNALLOWED = "\\[[^\\s\\-,]+\\-[0-9]+\\](,\\[[^\\s\\-,]+\\-[0-9]+\\])*";

    public static void validatePurchasingItems(String orderDetail) {
        if (delimiterNotInOrderOrBlankIncludedIn(orderDetail)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static boolean delimiterNotInOrderOrBlankIncludedIn(String inputValue) {
        return !inputValue.matches(DELIMITERS_IN_ORDER_REGEX_WITH_BLANK_UNALLOWED);
    }

    public static void validateYesOrNoAnswer(String answer) {
        for (Answer ans : Answer.values()) {
            if (ans.getFormat().equals(answer)) {
                return;
            }
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }
}
