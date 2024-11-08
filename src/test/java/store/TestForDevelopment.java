package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.exceptions.DidNotBringPromotionGiveProductException;
import store.exceptions.OutOfPromotionStockException;
import store.view.InputView;

public class TestForDevelopment {
    private Promotions promotions = Application.registerPromotions();
    private List<Promotion> promotionList = promotions.getPromotions();
    private Products products = Application.registerProducts(promotions);
    private InputView inputView = new InputView();

    @ParameterizedTest
    @CsvSource(value = {"0,탄산2+1,2,1,2024-01-01,2024-12-31", "1,MD추천상품,1,1,2024-01-01,2024-12-31",
            "2,반짝할인,1,1,2024-11-01,2024-11-30"}
            , delimiter = ',')
    void 프로모션_목록을_등록한다(int index, String name, int buy, int get, String startDateStr, String endDateStr) {
        String[] startDateArray = startDateStr.split("-");
        int startYear = Integer.parseInt(startDateArray[0]);
        int startMonth = Integer.parseInt(startDateArray[1]);
        int startDay = Integer.parseInt(startDateArray[2]);

        String[] endDateArray = endDateStr.split("-");
        int endYear = Integer.parseInt(endDateArray[0]);
        int endMonth = Integer.parseInt(endDateArray[1]);
        int endDay = Integer.parseInt(endDateArray[2]);

        assertThat(promotionList.get(index).getName()).isEqualTo(name);
        assertThat(promotionList.get(index).getBuyAmount()).isEqualTo(buy);
        assertThat(promotionList.get(index).getGetAmount()).isEqualTo(get);
        assertThat(promotionList.get(index).getStartDate().getYear()).isEqualTo(startYear);
        assertThat(promotionList.get(index).getStartDate().getMonthValue()).isEqualTo(startMonth);
        assertThat(promotionList.get(index).getStartDate().getDayOfMonth()).isEqualTo(startDay);
        assertThat(promotionList.get(index).getEndDate().getYear()).isEqualTo(endYear);
        assertThat(promotionList.get(index).getEndDate().getMonthValue()).isEqualTo(endMonth);
        assertThat(promotionList.get(index).getEndDate().getDayOfMonth()).isEqualTo(endDay);
    }

    @Test
    @Disabled
    void 비할인_프로모션_객체_등록() {
        assertThat(promotionList.get(3).getName()).isEqualTo("null");
        assertThat(promotionList.get(3).getBuyAmount()).isEqualTo(0);
        assertThat(promotionList.get(3).getGetAmount()).isEqualTo(0);
        assertThat(promotionList.get(3).getStartDate()).isNull();
        assertThat(promotionList.get(3).getEndDate()).isNull();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,콜라,1000,10,10,탄산2+1", "1,사이다,1000,8,7,탄산2+1", "2,오렌지주스,1800,9,0,MD추천상품", "3,탄산수,1200,5,0,탄산2+1"
            , "4,물,500,0,10,null", "5,비타민워터,1500,0,6,null", "6,감자칩,1500,5,5,반짝할인", "7,초코바,1200,5,5,MD추천상품"
            , "8,에너지바,2000,0,5,null", "9,정식도시락,6400,0,8,null", "10,컵라면,1700,1,10,MD추천상품"}
            , delimiter = ','
    )
    void 상품_목록을_등록한다(int index, String name, int price, int promotionQuantity, int regularQuantity,
                     String promotionName) {
        assertThat(products.getProducts().get(index).getName()).isEqualTo(name);
        assertThat(products.getProducts().get(index).getPrice()).isEqualTo(price);
        assertThat(products.getProducts().get(index).getPromotionQuantity()).isEqualTo(promotionQuantity);
        assertThat(products.getProducts().get(index).getRegularQuantity()).isEqualTo(regularQuantity);
        if (promotionName.equals("null")) {
            assertThatThrownBy(() -> products.getProducts().get(index).getPromotion().orElseThrow())
                    .isInstanceOf(NoSuchElementException.class);
        } else {
            assertThat(products.getProducts().get(index).getPromotion().orElseThrow().getName()).isEqualTo(promotionName);
        }
    }

    @Test
    void 상품_저장_시_프로모션_상품과_일반_상품을_통합해_저장한다() {
        assertThat(products.findByName("사이다").hasOngoingPromotion()).isTrue();
        assertThat(products.findByName("사이다").getPromotionQuantity()).isEqualTo(8);
        assertThat(products.findByName("사이다").getRegularQuantity()).isEqualTo(7);
    }

    @ParameterizedTest
    @CsvSource(value = {"2023,1,1,false", "2024,1,1,false", "2024,11,1,true", "2024,12,1,false", "2025,1,1,false"}
            , delimiter = ',')
    void 날짜에_맞는_진행중인_프로모션을_반환한다(int year, int month, int day, boolean promotionDuringNovemberOngoing) {
        LocalDate localDate = LocalDate.of(year, month, day);

        Promotion promotion = new Promotion("test", "2", "1"
                , "2024-11-01", "2024-11-30", localDate);

        assertThat(promotion.isOngoing()).isEqualTo(promotionDuringNovemberOngoing);
    }

    @Test
    void 상품명과_수량_문자열을_분리한다() {
        String testCart = "[콜라-3],[에너지바-5]";

        Cart cart = Application.registerCart(testCart, products);

        assertThat(cart.getCart().size()).isEqualTo(2);
    }

    @Test
    void 잘못된_상품명_입력시_Application에서_예외를_반환한다() {
        String testCart = "[코카콜라-3],[에너지바-5]";

        assertThatIllegalArgumentException().isThrownBy(
                        () -> Application.registerCart(testCart, products))
                .withMessage("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-0],[에너지바-5]", "[콜라--1],[에너지바-5]", "[콜라-0.5],[에너지바-5]"})
    void 구매수량_양의정수_아닐시_Application에서_예외를_반환한다(String testCart) {
        assertThatIllegalArgumentException().isThrownBy(
                        () -> Application.registerCart(testCart, products))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"{콜라-3],[에너지바-5]", "[콜라~3],[에너지바-5]", "[콜라-3]&[에너지바-5]"})
    void 올바르지_않은_구분자_입력시_Application에서_예외를_반환한다(String testCart) {
        assertThatIllegalArgumentException().isThrownBy(
                        () -> Application.registerCart(testCart, products))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"{콜라-3],[에너지바-5]", "[콜라~3],[에너지바-5]", "[콜라-3]&[에너지바-5]"})
    void 올바르지_않은_구분자_입력시_입력단에서_예외를_반환한다(String testCart) {
        assertThatIllegalArgumentException().isThrownBy(
                        () -> inputView.validateInputPurchasingProducts(testCart))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @Test
    void 구매_상품의_재고_수량을_확인한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Product testProduct = new Product(
                "test", "1000", "10", Optional.empty());
        cartMap.put(testProduct, 3);
        Cart cart = new Cart(cartMap);
        assertThatCode(() -> cart.checkStock())
                .doesNotThrowAnyException();
    }

    @Test
    void 구매_상품의_재고가_부족하면_예외를_반환한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Product testProduct = new Product(
                "test", "1000", "10", Optional.empty());
        cartMap.put(testProduct, 20);
        Cart cart = new Cart(cartMap);
        assertThatIllegalArgumentException().isThrownBy(
                        () -> cart.checkStock())
                .withMessage("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    @Test
    void 프로모션_상품이_없으면_그냥_결제한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("onlyRegular"), 3);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        customer.applyPromotion();

        assertThat(testProducts.findByName("onlyRegular").getRegularQuantity()).isEqualTo(0);
        assertThat(customer.getPromotionGetProducts()).isEmpty();
    }

    @Test
    void 프로모션_상품이_존재하고_프로모션_제공_개수랑_맞으면_결제한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("withPromotion"), 3);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        customer.applyPromotion();

        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(2);
        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
        assertThat(customer.getPromotionGetProducts()).containsExactly(testProducts.findByName("withPromotion"));
    }

    @Test
    void 프로모션_상품이_존재하고_프로모션_제공_개수의_2배면_2개를_증정하며_결제한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "6", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("withPromotion"), 6);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        customer.applyPromotion();

        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
        assertThat(customer.getPromotionGetProducts().size()).isEqualTo(2);
        assertThat(customer.getPromotionGetProducts()).contains(testProducts.findByName("withPromotion"));
    }

    @Test
    void 프로모션_상품이_존재하고_프로모션_제공_개수랑_맞는데_프로모션_재고가_부족하면_예외_발생() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("withPromotion"), 6);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        assertThatThrownBy(() -> customer.applyPromotion())
                .isInstanceOf(OutOfPromotionStockException.class);
    }

    @Test
    void 프로모션_상품이_존재하는데_프로모션_증정_상품을_안_가져왔을때_재고가_남아있으면_예외_발생() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "6", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("withPromotion"), 5);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        assertThatThrownBy(() -> customer.applyPromotion())
                .isInstanceOf(DidNotBringPromotionGiveProductException.class);
    }

    @Test
    void 프로모션_상품이_존재하고_프로모션_증정_상품을_안_가져왔는데_재고가_모자라면_증정하지_않고_계산한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "4", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("withPromotion"), 5);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        customer.applyPromotion();

        assertThat(customer.getPromotionGetProducts().size()).isEqualTo(1);
        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(4);
    }

    @Test
    void 프로모션_상품이_존재하고_프로모션_제공_개수와_맞지_않으면_그냥_결제한다() {
        Map<Product, Integer> cartMap = new HashMap<>();
        Products testProducts = new Products();
        testProducts.registerProduct(
                "withPromotion", "1000", "5", Optional.empty());
        testProducts.registerProduct(
                "withPromotion", "1000", "4", promotions.findByName("탄산2+1"));
        testProducts.registerProduct(
                "onlyRegular", "1000", "3", Optional.empty());
        cartMap.put(testProducts.findByName("withPromotion"), 4);
        Cart cart = new Cart(cartMap);
        Customer customer = new Customer(cart);

        customer.applyPromotion();

        assertThat(customer.getPromotionGetProducts().size()).isEqualTo(1);
        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
    }
}
