package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import store.exceptions.NotFoundByNameException;
import store.util.FileScanner;

public class Items {
    private final List<Item> items;

    private Items(List<Item> items) {
        this.items = items;
    }

    public static Items register(Promotions promotions) {
        try {
            List<String> itemFileData = FileScanner.readFile("./src/main/resources/products.md");
            List<Item> items = new ArrayList<>();
            for (String itemData : itemFileData) {
                String[] item = itemData.split(",");
                Optional<Item> registeredItem = findByNameOrElseEmpty(item[0], items);
                if (registeredItem.isEmpty()) {
                    items.add(Item.from(
                            item[0], item[1], item[2], item[3], promotions
                    ));
                    continue;
                }
                registeredItem.get().update(item[2], item[3], promotions);
            }
            validateNameDuplication(items);
            return new Items(items);
        } catch (NullPointerException e) {
            throw new IllegalStateException("[SYSTEM] 잘못된 상품입니다.");
        }
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
