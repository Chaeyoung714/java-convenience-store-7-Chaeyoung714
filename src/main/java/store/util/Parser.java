package store.util;

import static store.exceptions.ExceptionMessages.WRONG_ORDER_FORMAT;

import java.util.HashMap;
import java.util.Map;
import store.exceptions.ExceptionMessages;

public class Parser {
    private static final String DELIMITER_BETWEEN_EACH_ORDER = ",";
    private static final String DELIMITER_BETWEEN_NAME_AND_AMOUNT = "-";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final int ARRAY_SIZE_OF_ORDER_DETAIL = 2;
    private static final int ITEM_NAME = 0;
    private static final int BUY_AMOUNT = 1;

    private Parser() {
    }

    public static Map<String, Integer> parseOrderDetails(String orderDetailInput) throws IllegalArgumentException {
        try {
            Map<String, Integer> parsedOrderDetails = new HashMap<>();
            String[] orderDetails = orderDetailInput.split(DELIMITER_BETWEEN_EACH_ORDER);
            for (String orderDetail : orderDetails) {
                parseEachOrderDetail(orderDetail, parsedOrderDetails);
            }
            return parsedOrderDetails;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
        }
    }

    private static void parseEachOrderDetail(String unparsedOrderDetail, Map<String, Integer> parsedOrderDetails) {
        validateBracketDelimiter(unparsedOrderDetail);
        String[] parsedOrderDetail = unparsedOrderDetail.substring(1, unparsedOrderDetail.length() - 1)
                .split(DELIMITER_BETWEEN_NAME_AND_AMOUNT);
        validateHyphenDelimiter(parsedOrderDetail);
        addOrderDetail(parsedOrderDetail[ITEM_NAME], parsedOrderDetail[BUY_AMOUNT], parsedOrderDetails);
    }

    private static void addOrderDetail(String itemName, String buyAmount, Map<String, Integer> parsedOrderDetails) {
        try {
            if (parsedOrderDetails.containsKey(itemName)) {
                parsedOrderDetails.replace(itemName, parsedOrderDetails.get(itemName) + Integer.parseInt(buyAmount));
                return;
            }
            parsedOrderDetails.put(itemName, Integer.parseInt(buyAmount));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(WRONG_ORDER_FORMAT.getMessage());
        }
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
