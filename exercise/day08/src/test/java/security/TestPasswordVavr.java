package security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TestPasswordVavr {

    @Test
    void a_valid_case() {
        assertThat(PasswordVavr.of("aA1.5678").isSuccess()).isTrue();
    }

    @ParameterizedTest(name="{0} is accepted as special char")
    @MethodSource
    void the_following_special_chars_are_accepted(char special_char) {
        assertThat(PasswordVavr.of("aA34567" + special_char).isSuccess()).isTrue();
    }
    private static Stream<Arguments> the_following_special_chars_are_accepted() {
        return Stream.of(
                Arguments.of("."),
                Arguments.of("*"),
                Arguments.of("#"),
                Arguments.of("@"),
                Arguments.of("$"),
                Arguments.of("%"),
                Arguments.of("&")
        );
    }

    @ParameterizedTest(name="{0} => {1}")
    @MethodSource
    void invalid_cases(String password, Class<Exception> type) {
        assertThat(PasswordVavr.of(password).isFailure()).isTrue();
        assertThat(PasswordVavr.of(password).getCause()).isInstanceOf(type);
    }
    private static Stream<Arguments> invalid_cases() {
        return Stream.of(
                Arguments.of("aa1.5678", PasswordWithoutUpperCaseException.class)
        );
    }

    @Test
    void rejected_when_there_is_less_than_8_chars() {
        assertThat(PasswordVavr.of("aA1.567").isFailure()).isTrue();
    }

    @Test
    void rejected_when_a_capital_letter_is_missing() {
        assertThat(PasswordVavr.of("aa1.5678").isFailure()).isTrue();
        assertThat(PasswordVavr.of("aa1.5678").getCause()).isInstanceOf(PasswordWithoutUpperCaseException.class);
    }

    @Test
    void rejected_when_a_lowercase_is_missing() {
        assertThat(PasswordVavr.of("AA1.5678").isFailure()).isTrue();
        assertThat(PasswordVavr.of("aa1.5678").getCause()).isInstanceOf(PasswordWithoutUpperCaseException.class);
    }

    @Test
    void rejected_when_a_number_is_missing() {
        assertThat(PasswordVavr.of("aA1.5678").isSuccess()).isTrue();
        assertThat(PasswordVavr.of("aAa.aaaa").isFailure()).isTrue();
    }

    @Test
    void rejected_when_a_special_char_is_missing() {
        assertThat(PasswordVavr.of("aA1.5678").isSuccess()).isTrue();
        assertThat(PasswordVavr.of("aA1a5678").isFailure()).isTrue();
    }

    @Test
    void rejected_when_an_unauthorized_char_is_present() {
        assertThat(PasswordVavr.of("aA1.....").isSuccess()).isTrue();
        assertThat(PasswordVavr.of("aA1....ù").isFailure()).isTrue();
    }
}