package functional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TestPassword {

    @Test
    void a_valid_case() {
        Assertions.assertThat(Password.of("aA1.5678").isSuccess()).isTrue();
    }

    @ParameterizedTest(name="{0} throws {1}")
    @MethodSource
    void invalid_cases(String password, Class<Exception> type) {
        assertThat(Password.of(password).isFailure()).isTrue();
        assertThat(Password.of(password).getCause()).isInstanceOf(type);
    }
    private static Stream<Arguments> invalid_cases() {
        return Stream.of(
                Arguments.of("aA1.567", LessThanEightCharactersException.class),
                Arguments.of("aa1.5678", PasswordWithoutUpperCaseException.class),
                Arguments.of("AA1.5678", PasswordWithoutLowerCaseException.class),
                Arguments.of("aAa.aaaa", PasswordWithoutNumberException.class),
                Arguments.of("aA1a5678", PasswordWithoutSpecialCharacterException.class),
                Arguments.of("aA1....ù", PasswordWithoutUnauthorizedCharacterException.class)
        );
    }
}