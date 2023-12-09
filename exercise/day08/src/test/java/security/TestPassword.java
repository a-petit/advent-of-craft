package security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TestPassword {

    private String A_VALID_PASSWORD = "aA1.5678";

    @Test
    void a_valid_case() {
        assertThat(Password.of("aA1.5678")).isEqualTo(true);
    }

    @ParameterizedTest(name="{0} is accepted as special char")
    @MethodSource
    void the_following_special_chars_are_accepted(char special_char) {
        assertThat(Password.of("aA34567" + special_char)).isEqualTo(true);
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

    @Test
    void rejected_when_there_is_less_than_8_chars() {
        assertThat(Password.of("aA1.5678")).isEqualTo(true);
        assertThat(Password.of("aA1.567")).isEqualTo(false);
    }

    @Test
    void rejected_when_a_capital_letter_is_missing() {
        assertThat(Password.of("aA1.5678")).isEqualTo(true);
        assertThat(Password.of("aa1.5678")).isEqualTo(false);
    }

    @Test
    void rejected_when_a_lowercase_is_missing() {
        assertThat(Password.of("aA1.5678")).isEqualTo(true);
        assertThat(Password.of("AA1.5678")).isEqualTo(false);
    }

    @Test
    void rejected_when_a_number_is_missing() {
        assertThat(Password.of("aA1.5678")).isEqualTo(true);
        assertThat(Password.of("aAa.aaaa")).isEqualTo(false);
    }

    @Test
    void rejected_when_a_special_char_is_missing() {
        assertThat(Password.of("aA1.5678")).isEqualTo(true);
        assertThat(Password.of("aA1a5678")).isEqualTo(false);
    }

    @Test
    void rejected_when_an_unauthorized_char_is_present() {
        assertThat(Password.of("aA1.....")).isEqualTo(true);
        assertThat(Password.of("aA1....ù")).isEqualTo(false);
    }
}