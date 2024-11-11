package store.dto.output;

import java.util.ArrayList;
import java.util.List;
import store.model.consumer.Cart;
import store.model.item.Item;

public record PurchaseHistoryDtos(List<PurchaseHistoryDto> dtos) {

    public static PurchaseHistoryDtos of(Cart cart) {
        List<PurchaseHistoryDto> dtos = new ArrayList<>();
        for (Item item : cart.getCart().keySet()) {
            dtos.add(new PurchaseHistoryDto(
                    item.getName()
                    , cart.getCart().get(item)
                    , cart.getCart().get(item) * item.getPrice()));
        }
        return new PurchaseHistoryDtos(dtos);
    }

    public record PurchaseHistoryDto(String name, int buyAmount, int totalCost) {
    }
}