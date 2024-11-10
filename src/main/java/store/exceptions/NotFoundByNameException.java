package store.exceptions;

public class NotFoundByNameException extends RuntimeException{
    private final String message;

    public NotFoundByNameException() {
        this.message = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
