package store.util;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static Map<String, String> parseOrderDetails(String orderDetailInput) {
        try {
            Map<String, String> parsedOrderDetails = new HashMap<>();
            String[] orderDetails = orderDetailInput.split(",");
            for (String orderDetail : orderDetails) {
                validateBracketDelimiter(orderDetail);
                String[] parsedOrderDetail = orderDetail.substring(1, orderDetail.length() - 1)
                        .split("-");
                validateHyphenDelimiter(parsedOrderDetail);
                parsedOrderDetails.put(parsedOrderDetail[0], parsedOrderDetail[1]);
            }
            return parsedOrderDetails;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateBracketDelimiter(String orderDetail) {
        if (!orderDetail.startsWith("[") || !orderDetail.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateHyphenDelimiter(String[] parsedOrder) {
        if (parsedOrder.length != 2) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
