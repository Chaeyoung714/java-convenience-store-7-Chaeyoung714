package store;


public class TestForDevelopment {
//    private Promotions promotions = Application.registerPromotions();
//    private List<Promotion> promotionList = promotions.getPromotions();
//    private Products products = Application.registerProducts(promotions);
//    private InputView inputView = new InputView();

//
//

//
//

//
//    @ParameterizedTest
//    @ValueSource(strings = {"{콜라-3],[에너지바-5]", "[콜라~3],[에너지바-5]", "[콜라-3]&[에너지바-5]"})
//    void 올바르지_않은_구분자_입력시_입력단에서_예외를_반환한다(String testCart) {
//        assertThatIllegalArgumentException().isThrownBy(
//                        () -> inputView.validateInputPurchasingProducts(testCart))
//                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
//    }
//

//
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_제공_개수랑_맞으면_결제한다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 3);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        customer.applyPromotion();
//
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(2);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//        assertThat(customer.getPromotionGetProducts().containsKey(testProducts.findByName("withPromotion"))).isTrue();
//    }
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_제공_개수의_2배면_2개를_증정하며_결제한다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "6", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 6);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        customer.applyPromotion();
//
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(2);
//        assertThat(customer.getPromotionGetProducts().containsKey(testProducts.findByName("withPromotion"))).isTrue();
//    }
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_제공_개수랑_맞는데_프로모션_재고가_부족하면_예외_발생() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 6);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        assertThatThrownBy(() -> customer.applyPromotion())
//                .isInstanceOf(OutOfPromotionStockException.class);
//    }
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_증정_상품을_안_가져왔는데_기존_프로모션_재고가_부족하면_예외_발생() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "2", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 5);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        assertThatThrownBy(() -> customer.applyPromotion())
//                .isInstanceOf(OutOfPromotionStockException.class);
//    }
//
//    @Test
//    void 프로모션_상품이_존재하는데_프로모션_증정_상품을_안_가져왔을때_재고가_남아있으면_예외_발생() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "6", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 5);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        assertThatThrownBy(() -> customer.applyPromotion())
//                .isInstanceOf(DidNotBringPromotionGiveProductException.class);
//    }
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_증정_상품을_안_가져왔는데_재고가_모자라면_증정하지_않고_계산한다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "4", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 5);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        customer.applyPromotion();
//
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(1);
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(4);
//    }
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_제공_개수와_맞지_않으면_그냥_결제한다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "4", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 4);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        customer.applyPromotion();
//
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(1);
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//    }
//
//    @Test
//    void 프로모션_상품이_존재하고_프로모션_제공_개수와_맞지_않아서_그냥_결제할때_프로모션_재고가_모자라면_예외_발생() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "2", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 4);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        assertThatThrownBy(() -> customer.applyPromotion())
//                .isInstanceOf(OutOfPromotionStockException.class);
//    }
//
//    @Test
//    void 프로모션_상품이_존재하지만_프로모션이_적용이_안되는_개수만_구매한다면_그냥_계산한다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//
//        Map<Product, Integer> cartMap = new HashMap<>();
//        cartMap.put(testProducts.findByName("withPromotion"), 1);
//        Cart cart = new Cart(cartMap);
//        Customer customer = new Customer(cart);
//
//        customer.applyPromotion();
//
//        assertThat(customer.getPromotionGetProducts().containsKey(testProducts.findByName("withPromotion"))).isFalse();
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(4);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//    }
//
//    @Test
//    @DisplayName("/////////////프로모션 재고가 부족할 때, 부족한 양은 기존 정가로 계산한다고 답함")
//    void 프로모션_재고가_부족하면_부족한_양은_기존_정가로_계산한다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Customer customer = new Customer(new Cart(new HashMap<>()));
//
//        customer.buyIncludingOutOfStockAmount(testProducts.findByName("withPromotion"), 3, 6);
//
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(1);
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(4);
//    }
//
//    @Test
//    @DisplayName("프로모션 재고가 부족할 때, 부족한 양은 사지 않겠다고 답함")
//    void 프로모션_재고가_부족하면_부족한_양은_구매하지_않는다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//
//        Map<Product, Integer> carMap = new HashMap<>();
//        carMap.put(testProducts.findByName("withPromotion"), 6);
//        Cart cart = new Cart(carMap);
//        Customer customer = new Customer(cart);
//
//        customer.buyExcludingOutOfStockAmount(testProducts.findByName("withPromotion"), 3, 6);
//
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(1);
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(2);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//        assertThat(cart.getCart().get(testProducts.findByName("withPromotion"))).isEqualTo(3);
//    }
//
//    @Test
//    @DisplayName("프로모션 증정품을 덜 가져왔을 때, 가져오겠다고 답함")
//    void 프로모션_증정_상품을_덜_가져왔을때_가져온다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "6", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//
//        Map<Product, Integer> carMap = new HashMap<>();
//        carMap.put(testProducts.findByName("withPromotion"), 5);
//        Cart cart = new Cart(carMap);
//        Customer customer = new Customer(cart);
//
//        customer.buyIncludingPromotionGetProduct(testProducts.findByName("withPromotion"), 5);
//
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(2);
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(0);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//        assertThat(cart.getCart().get(testProducts.findByName("withPromotion"))).isEqualTo(6);
//    }
//
//    @Test
//    @DisplayName("프로모션 증정품을 덜 가져왔을 때, 가져오지 않겠다고 답함")
//    void 프로모션_증정_상품을_덜_가져왔을때_가져오지_않는다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "withPromotion", "1000", "5", Optional.empty());
//        testProducts.registerProduct(
//                "withPromotion", "1000", "6", promotions.findByName("탄산2+1"));
//        testProducts.registerProduct(
//                "onlyRegular", "1000", "3", Optional.empty());
//        Customer customer = new Customer(new Cart(new HashMap<>()));
//
//        customer.buyExcludingPromotionGetProduct(testProducts.findByName("withPromotion"), 5);
//
//        assertThat(customer.getPromotionGetProducts().get(testProducts.findByName("withPromotion"))).isEqualTo(1);
//        assertThat(testProducts.findByName("withPromotion").getPromotionQuantity()).isEqualTo(1);
//        assertThat(testProducts.findByName("withPromotion").getRegularQuantity()).isEqualTo(5);
//    }
//
//    @Test
//    void 최대금액_이하의_멤버십할인을_받는다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "test", "10000", "5", Optional.empty());
//
//        Map<Product, Integer> carMap = new HashMap<>();
//        carMap.put(testProducts.findByName("test"), 1);
//        Cart cart = new Cart(carMap);
//        Customer customer = new Customer(cart);
//
//        customer.applyMembership(0);
//
//        assertThat(customer.getMembershipDiscountAmount()).isEqualTo(3000);
//    }
//
//    @Test
//    void 프로모션_적용_금액을_제외하고_멤버십할인을_받는다() {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "test", "10000", "5", promotions.findByName("탄산2+1"));
//
//        Map<Product, Integer> carMap = new HashMap<>();
//        carMap.put(testProducts.findByName("test"), 5);
//        Cart cart = new Cart(carMap);
//        Customer customer = new Customer(cart);
//        customer.buyIncludingOutOfStockAmount(testProducts.findByName("test"), 2, 5);
//        int promotionAppliedAmount = customer.calculatePromotionAppliedAmount();
//
//        customer.applyMembership(promotionAppliedAmount);
//
//        assertThat(customer.getMembershipDiscountAmount()).isEqualTo(6000);
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"81000", "100000", "1000000"})
//    void 최대금액_이상의_멤버십할인을_받으면_최대금액만큼_할인받는다(String purchasePrice) {
//        Products testProducts = new Products();
//        testProducts.registerProduct(
//                "test", purchasePrice, "5", Optional.empty());
//
//        Map<Product, Integer> carMap = new HashMap<>();
//        carMap.put(testProducts.findByName("test"), 1);
//        Cart cart = new Cart(carMap);
//        Customer customer = new Customer(cart);
//
//
//        customer.applyMembership(0);
//
//        assertThat(customer.getMembershipDiscountAmount()).isEqualTo(8000);
//    }
}
