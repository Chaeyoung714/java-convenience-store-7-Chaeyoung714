package store.exceptions;

public enum ExceptionMessages {
    WRONG_ORDER_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ITEM_NOT_EXISTS("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    ORDER_EXCEEDS_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    WRONG_INPUT_VALUE("잘못된 입력입니다. 다시 입력해 주세요."),
    ;
    private final String message;

    ExceptionMessages(String message) {
        this.message = "[ERROR] " + message;
    }

    public String getMessage() {
        return message;
    }
}