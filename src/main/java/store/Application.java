package store;

import store.controller.ConvenienceStoreController;
import store.model.Items;
import store.model.Promotions;
import store.util.FileScanner;

public class Application {
    public static void main(String[] args) {

        Promotions promotions = Promotions.register(FileScanner.readFile("./src/main/resources/promotions.md"));
        Items items = Items.register(FileScanner.readFile("./src/main/resources/products.md"), promotions);

        DependencyConfig dependencyConfig = new DependencyConfig();
        ConvenienceStoreController controller = dependencyConfig.convenienceStoreController();

        controller.run(items);
    }
}
