package store.util;

import static store.exceptions.ExceptionMessages.WRONG_ORDER_FORMAT;

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


    public static Map<String, String> parseOrderDetails(String orderDetailInput) throws IllegalArgumentException {
        try {
            Map<String, String> parsedOrderDetails = new HashMap<>();
            String[] orderDetails = orderDetailInput.split(DELIMITER_BETWEEN_EACH_ORDER);
            for (String orderDetail : orderDetails) {
                parseEachOrderDetail(orderDetail, parsedOrderDetails);
            }
            return parsedOrderDetails;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
        }
    }

    private static void parseEachOrderDetail(String unparsedOrderDetail, Map<String, String> parsedOrderDetails) {
        validateBracketDelimiter(unparsedOrderDetail);
        String[] parsedOrderDetail = unparsedOrderDetail.substring(1, unparsedOrderDetail.length() - 1)
                .split(DELIMITER_BETWEEN_NAME_AND_AMOUNT);
        validateHyphenDelimiter(parsedOrderDetail);
        parsedOrderDetails.put(parsedOrderDetail[ITEM_NAME], parsedOrderDetail[BUY_AMOUNT]);
    }

    private static void validateBracketDelimiter(String orderDetail) {
        if (!orderDetail.startsWith(LEFT_BRACKET) || !orderDetail.endsWith(RIGHT_BRACKET)) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
        }
    }

    private static void validateHyphenDelimiter(String[] parsedOrder) {
        if (parsedOrder.length != ARRAY_SIZE_OF_ORDER_DETAIL) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
        }
    }
}
