package store.exceptions;

public class NotFoundByNameException extends RuntimeException{
    private final String message;

    public NotFoundByNameException() {
        this.message = ExceptionMessages.ITEM_NOT_EXISTS.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
