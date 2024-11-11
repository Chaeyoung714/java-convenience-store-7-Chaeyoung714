package store.dto.output;

import java.util.ArrayList;
import java.util.List;
import store.model.consumer.DiscountHistory;
import store.model.item.Item;

public record PromotionHistoryDtos(List<PromotionHistoryDto> dtos) {

    public static PromotionHistoryDtos of(DiscountHistory discountHistory) {
        List<PromotionHistoryDto> dtos = new ArrayList<>();
        for (Item item : discountHistory.getGifts().keySet()) {
            dtos.add(new PromotionHistoryDto(
                    item.getName(), discountHistory.getGifts().get(item)
            ));
        }
        return new PromotionHistoryDtos(dtos);
    }

    public record PromotionHistoryDto(String name, int amount) {
    }
}
