package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        Promotions promotions = registerPromotions();
        Products products = registerProducts(promotions);
        List<Promotion> ongoingPromotions = promotions.checkOngoingPromotionsOf(DateTimes.now().toLocalDate());
        outputView.printProudcts(products.getProducts());
        String purchasingProducts = inputView.readPurchasingProducts();
        registerCart(purchasingProducts, products);

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

    public static Products registerProducts(Promotions promotions) {
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
            return new Products(products);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null; // 리팩토링 예정
        }
    }

    public static Cart registerCart(String cartInput, Products products) {
        try {
            Map<Product, Integer> cart = new HashMap<>();
            Map<String, String> parsedCart = parseCart(cartInput);
            for (String productName : parsedCart.keySet()) {
                int buyAmount = Integer.parseInt(parsedCart.get(productName));
                if (buyAmount <= 0) {
                    throw new NumberFormatException();
                }
                cart.put(products.findByName(productName)
                        , Integer.parseInt(parsedCart.get(productName)));
            }
            return new Cart(cart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static Map<String, String> parseCart(String cartInput) {
        //입력단에서 검증 -> 여기서 한번 더 형태 검증 (split이 여러개라서 두단 검증 필요해보임)
        try {
            Map<String, String> cart = new HashMap<>();
            String[] buyProducts = cartInput.split(",");
            if (buyProducts.length > 0) { // ,로 split한 것 검증
                throw new IllegalArgumentException();
            }
            for (String buyProduct : buyProducts) {
                if (!buyProduct.startsWith("[") || !buyProduct.endsWith("]")) {
                    throw new IllegalArgumentException();
                }
                String subStringOfBuyProduct = buyProduct.substring(1, buyProduct.length() - 1);
                String[] buyProductNameAndAmount = subStringOfBuyProduct.split("-");
                if (buyProductNameAndAmount.length != 2) {
                    throw new IllegalArgumentException(); //-로 split한 것 검증
                }
                cart.put(buyProductNameAndAmount[0], buyProductNameAndAmount[1]);
            }
            return cart;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
