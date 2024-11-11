package store.model.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.promotion.Promotion;
import store.model.promotion.Promotions;

public class ItemsFactory {
    private static final String PRODUCTS_FILE_DELIMITER = ",";
    private static final int NAME = 0;
    private static final int PRICE = 1;
    private static final int QUANTITY = 2;
    private static final int PROMOTION = 3;

    public static Items of(final List<String> itemFileData, final Promotions promotions) {
        try {
            List<Item> items = parseItems(itemFileData, promotions);
            return new Items(items);
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] Wrong item quantity or price");
        }
    }

    private static List<Item> parseItems(final List<String> itemFileData, final Promotions promotions) {
        List<Item> items = new ArrayList<>();
        for (String itemData : itemFileData) {
            String[] item = itemData.split(PRODUCTS_FILE_DELIMITER);
            Optional<Item> registeredItem = findByNameOrElseEmpty(item[NAME], items);
            Optional<Promotion> promotionOfItem = promotions.findByName(item[PROMOTION]);
            if (registeredItem.isEmpty()) {
                items.add(ItemFactory.from(
                        item[NAME], item[PRICE], item[QUANTITY], promotionOfItem));
                continue;
            }
            registeredItem.get().updateItemInfo(item[QUANTITY], promotionOfItem);
        }
        return items;
    }

    private static Optional<Item> findByNameOrElseEmpty(final String name, final List<Item> registeredItems) {
        for (Item item : registeredItems) {
            if (item.getName().equals(name)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
