package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.File;
import java.io.IOException;
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
        promotions.checkOngoingPromotionsBetweenAvailable(DateTimes.now().toLocalDate());//ongoing 확인 주기 : 한번 구매 시작할때(재입력은 따로 침)
        Products products = registerProducts(promotions);
        outputView.printProudcts(products.getProducts());
        String purchasingProducts = inputView.readPurchasingProducts();
        Cart cart = registerCart(purchasingProducts, products);
        checkStockOfCartProducts(cart, products);
    }

    public static Promotions registerPromotions() {
        try {
            List<Promotion> promotions = new ArrayList<>();
            Scanner scanner = new Scanner(new File(
                    "./src/main/resources/promotions.md"));
            scanner.next(); //ignore first line
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
            scanner.next(); //ignore first line
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
            Map<String, String> parsedCart = parseCart(cartInput);
            Map<String, Integer> cart = new HashMap<>();
            for (String productName : parsedCart.keySet()) {
                int buyAmount = Integer.parseInt(parsedCart.get(productName));
                if (buyAmount <= 0) {
                    throw new NumberFormatException();
                }
                if (!products.hasName(productName)) {
                    throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
                };
                cart.put(productName, buyAmount);
            }
            return new Cart(cart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static Map<String, String> parseCart(String cartInput) {
        //입력단에서 검증 -> 여기서 한번 더 형태 검증 (split이 여러개라서 두단 검증 필요해보임)
        try {
            Map<String, String> cart = new HashMap<>();
            String[] buyProducts = cartInput.split(",");
            if (buyProducts.length <= 0) { // ,로 split한 것 검증
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

    public static void checkStockOfCartProducts(Cart cart, Products products) {
        for (String productName : cart.getAllProductNames()) {
            List<Product> buyProducts = products.findByName(productName);
            int totalStockQuantity = 0;
            for (Product product : buyProducts) {
                totalStockQuantity += product.getQuantity();
            }
            if (totalStockQuantity < cart.getBuyCountByName(productName)) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }
}
