package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import store.exceptions.NotFoundByNameException;

public class Items {
    private static final String PRODUCTS_FILE_DELIMITER = ",";
    private static final int NAME = 0;
    private static final int PRICE = 1;
    private static final int QUANTITY = 2;
    private static final int PROMOTION = 3;

    private final List<Item> items;

    private Items(List<Item> items) {
        this.items = items;
    }

    public static Items register(List<String> itemFileData, Promotions promotions) {
        try {
            List<Item> items = parseItems(itemFileData, promotions);
            validateNameDuplication(items);
            return new Items(items);
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 상품입니다.");
        }
    }

    private static List<Item> parseItems(List<String> itemFileData, Promotions promotions) {
        List<Item> items = new ArrayList<>();
        for (String itemData : itemFileData) {
            String[] item = itemData.split(PRODUCTS_FILE_DELIMITER);
            Optional<Item> registeredItem = findByNameOrElseEmpty(item[NAME], items);
            Optional<Promotion> promotionOfItem = promotions.findByName(item[PROMOTION]);
            if (registeredItem.isEmpty()) {
                items.add(ItemFactory.from(
                        item[NAME], item[PRICE], item[QUANTITY], promotionOfItem
                ));
                continue;
            }
            registeredItem.get().updateItemInfo(item[QUANTITY], promotionOfItem);
        }
        return items;
    }

    public Item findByName(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new NotFoundByNameException();
    }

    private static Optional<Item> findByNameOrElseEmpty(String name, List<Item> registeredItems) {
        for (Item item : registeredItems) {
            if (item.getName().equals(name)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    private static void validateNameDuplication(List<Item> items) {
        Set<String> uniqueNames = new HashSet<>();
        for (Item item : items) {
            if (!uniqueNames.add(item.getName())) {
                throw new IllegalStateException("[SYSTEM] 중복된 상품명입니다.");
            }
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
