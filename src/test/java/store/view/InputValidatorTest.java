package store.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.exceptions.ExceptionMessages;

public class InputValidatorTest {
    @ParameterizedTest
    @DisplayName("[구매내역입력][fail] 구매내역마다 대괄호를 사용하지 않으면 에외가 발생한다.")
    @ValueSource(strings = {"[콜라-5},[사이다-3]", "{콜라-5],[사이다-3]", "콜라-5-[사이다-3]", "[[콜라-5]],[사이다-3]"})
    void fail_ifWrongBracket(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[구매내역입력][fai] 하이픈을 구매 상품 이름과 개수 사이의 구분자로 사용하지 않으면 예외가 발생한다.")
    @ValueSource(strings = {"[콜라5],[사이다-3]", "[콜라~5],[사이다-3]", "[콜라[5],[사이다-3]", "[콜라,5]-[사이다-3]"})
    void fail_ifWrongHyphenDelimiter(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[구매내역입력][fail] 쉼표를 각 구매 내역 간 구분자로 사용하지 않으면 예외가 발생한다.")
    @ValueSource(strings = {"[콜라-5][사이다-3]", "[콜라-5]-[사이다-3]", "[콜라-5],,[사이다-3]", "[콜라-5]&[사이다-3]"})
    void fail_ifWrongCommaDelimiter(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[구매내역입력][fail] 세 종류의 구분자 중 하나라도 덜 쓴다면 예외가 발생한다.")
    @ValueSource(strings = {"[콜라-5],", "[콜라-5,[사이다-3]", "[콜라-5"})
    void fail_ifDelimitersAreNotCompleted(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[구매내역입력][fail] 입력값 사이에 띄어쓰기가 들어가면 예외가 발생한다.")
    @ValueSource(strings = {"[콜라 - 5],[사이다-3]", "[콜라-5], [사이다-3]", "[ 콜라-5 ],[사이다-3]", "[콜라-5],[사이다-3] "})
    void fail_ifBlankIsIncluded(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage(ExceptionMessages.WRONG_ORDER_FORMAT.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[구매내역입력][fail] 입력값이 다 빈칸이면 예외가 발생한다.")
    @ValueSource(strings = {"", "  "})
    void fail_ifNullOrBlank(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage(ExceptionMessages.WRONG_INPUT_VALUE.getMessage());
    }

    @ParameterizedTest
    @DisplayName("[YN입력][fail] 입력값이 Y나 N이 아니면 예외가 발생한다.")
    @ValueSource(strings = {"", "  ", "y", "n", "YN", "yes", "no"})
    void fail_ifNotMatchesFixedFormat(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validateYesOrNoAnswer(wrongInput))
                .withMessage(ExceptionMessages.WRONG_INPUT_VALUE.getMessage());
    }
}
