package store.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"[콜라-5},[사이다-3]", "{콜라-5],[사이다-3]", "콜라-5-[사이다-3]", "[[콜라-5]],[사이다-3]"})
    void 대괄호를_사용하지_않으면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라5],[사이다-3]", "[콜라~5],[사이다-3]", "[콜라[5],[사이다-3]", "[콜라,5]-[사이다-3]"})
    void 하이픈을_사용하지_않으면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-5][사이다-3]", "[콜라-5]-[사이다-3]", "[콜라-5],,[사이다-3]", "[콜라-5]&[사이다-3]"})
    void 쉼표를_사용하지_않으면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-5],", "[콜라-5,[사이다-3]", "[콜라-5"})
    void 구분자를_마무리하지_않으면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라 - 5],[사이다-3]", "[콜라-5], [사이다-3]", "[ 콜라-5 ],[사이다-3]", "[콜라-5],[사이다-3] "})
    void 입력값에_빈칸이_들어가면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void 입력값이_빈칸이면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validatePurchasingItems(wrongInput))
                .withMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "y", "n", "YN", "yes", "no"})
    void 입력값이_Y나_N이_아니면_예외가_발생한다(String wrongInput) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                        () -> InputValidator.validateYesOrNoAnswer(wrongInput))
                .withMessage("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }
}
