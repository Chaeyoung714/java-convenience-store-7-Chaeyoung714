package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        Promotions promotions = registerPromotions();
        List<Product> products = registerProducts(promotions);
        List<Promotion> ongoingPromotions = promotions.checkOngoingPromotionsOf(DateTimes.now().toLocalDate());
        OutputView outputView = new OutputView();
        outputView.printProudcts(products);
    }

    public static Promotions registerPromotions() {
        try {
            List<Promotion> promotions = new ArrayList<>();
            Scanner scanner = new Scanner(new File(
                    "./src/main/resources/promotions.md"));
            String ignore = scanner.next();
            while (scanner.hasNext()) {
                String[] promotionInput = scanner.next().split(",");
                promotions.add(new Promotion(
                        promotionInput[0], promotionInput[1], promotionInput[2], promotionInput[3], promotionInput[4]
                ));
            }
            return new Promotions(promotions);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null; // 리팩토링 예정
        }
    }

    public static List<Product> registerProducts(Promotions promotions) {
        try {
            List<Product> products = new ArrayList<>();
            Scanner scanner = new Scanner(new File(
                    "./src/main/resources/products.md"));
            String ignore = scanner.next();
            while (scanner.hasNext()) {
                String[] productInput = scanner.next().split(",");
                products.add(new Product(
                        productInput[0], productInput[1], productInput[2]
                        , promotions.findByName(productInput[3])
                ));
            }
            return products;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>(); // 리팩토링 예정
        }
    }
}
