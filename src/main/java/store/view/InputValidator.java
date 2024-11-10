package store.view;

import static store.exceptions.ExceptionMessages.WRONG_INPUT_VALUE;
import static store.exceptions.ExceptionMessages.WRONG_ORDER_FORMAT;

import store.util.Answer;

public class InputValidator {
    private static final String DELIMITERS_IN_ORDER_REGEX_WITH_BLANK_UNALLOWED = "\\[[^\\s\\-,]+\\-[0-9]+\\](,\\[[^\\s\\-,]+\\-[0-9]+\\])*";

    public static void validatePurchasingItems(String orderDetail) {
        if (orderDetail == null || orderDetail.isBlank()) {
            throw new IllegalArgumentException(WRONG_INPUT_VALUE.getMessage());
        }
        if (delimiterNotInOrderOrBlankIncludedIn(orderDetail)) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
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
        throw new IllegalArgumentException(WRONG_INPUT_VALUE.getMessage());
    }
}
