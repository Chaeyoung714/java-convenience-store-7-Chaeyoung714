package store.exceptions;

import store.dto.OutOfStockPromotionDto;
import store.model.Item;

public class OutOfPromotionStockException extends RuntimeException{
    private final OutOfStockPromotionDto outOfStockPromotionDto;

    public OutOfPromotionStockException(Item item, int buyAmount) {
        this.outOfStockPromotionDto = OutOfStockPromotionDto.from(item, buyAmount);
    }

    public OutOfStockPromotionDto getOutOfStockPromotionDto() {
        return outOfStockPromotionDto;
    }
}
