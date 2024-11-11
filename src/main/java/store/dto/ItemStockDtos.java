package store.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.item.Item;
import store.model.item.Items;
import store.model.promotion.Promotion;

public record ItemStockDtos(List<ItemStockDto> dtos) {

    public static ItemStockDtos of(Items items) {
        List<ItemStockDto> dtos = new ArrayList<>();
        for (Item item : items.getItems()) {
            dtos.add(ItemStockDto.from(
                    item.getName()
                    , item.getPrice()
                    , item.getRegularQuantity()
                    , item.getPromotionQuantity()
                    , item.hasOngoingPromotion()
                    , item.getPromotion()
            ));
        }
        return new ItemStockDtos(dtos);
    }

    public record ItemStockDto(String name, int price, int regularQuantity, int promotionQuantity,
                               String promotionName) {
        public static ItemStockDto from(String name, int price, int regularQuantity, int promotionQuantity,
                                        boolean hasOngoingPromotion, Optional<Promotion> promotion) {
            String promotionName = "";
            if (hasOngoingPromotion) {
                promotionName = promotion.get().getName();
            }
            return new ItemStockDto(name, price, regularQuantity, promotionQuantity, promotionName);
        }
    }
}
