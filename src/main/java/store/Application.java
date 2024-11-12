package store;

import store.controller.ConvenienceStoreController;
import store.model.item.Items;
import store.model.item.ItemsFactory;
import store.model.promotion.Promotions;
import store.util.FileScanner;

public class Application {
    public static void main(String[] args) {
        DependencyConfig dependencyConfig = new DependencyConfig();
        ConvenienceStoreController controller = dependencyConfig.convenienceStoreController();

        controller.run();
    }
}
