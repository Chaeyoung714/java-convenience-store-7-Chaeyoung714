package store.util;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static final String DELIMITER_BETWEEN_EACH_ORDER = ",";
    private static final String DELIMITER_BETWEEN_NAME_AND_AMOUNT = "-";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final int ARRAY_SIZE_OF_ORDER_DETAIL = 2;
    private static final int ITEM_NAME = 0;
    private static final int BUY_AMOUNT = 1;


    public static Map<String, String> parseOrderDetails(String orderDetailInput) {
        try {
            Map<String, String> parsedOrderDetails = new HashMap<>();
            String[] orderDetails = orderDetailInput.split(DELIMITER_BETWEEN_EACH_ORDER);
            for (String orderDetail : orderDetails) {
                validateBracketDelimiter(orderDetail);
                String[] parsedOrderDetail = orderDetail.substring(1, orderDetail.length() - 1)
                        .split(DELIMITER_BETWEEN_NAME_AND_AMOUNT);
                validateHyphenDelimiter(parsedOrderDetail);
                parsedOrderDetails.put(parsedOrderDetail[ITEM_NAME], parsedOrderDetail[BUY_AMOUNT]);
            }
            return parsedOrderDetails;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateBracketDelimiter(String orderDetail) {
        if (!orderDetail.startsWith(LEFT_BRACKET) || !orderDetail.endsWith(RIGHT_BRACKET)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateHyphenDelimiter(String[] parsedOrder) {
        if (parsedOrder.length != ARRAY_SIZE_OF_ORDER_DETAIL) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
