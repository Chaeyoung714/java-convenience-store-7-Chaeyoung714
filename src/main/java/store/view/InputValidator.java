package store.view;

public class InputValidator {
    public static void validatePurchasingItems(String purchasingItems) {
        if (!purchasingItems.matches("\\[[^\\s\\]]+-[^\\s\\]]+\\](,\\[[^\\s\\]]+-[^\\s\\]]+\\])*")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
