package games;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConvertingValue {

    @ParameterizedTest
    @MethodSource
    void divisible_by_15(int input) throws OutOfRangeException {
        is(input, "FizzBuzz");
    }

    private static Stream<Arguments> divisible_by_15() {
        return Stream.of(
                Arguments.of(15),
                Arguments.of(15 * 2),
                Arguments.of(15 * 3)
        );
    }

    @ParameterizedTest
    @MethodSource
    void divisible_by_5_but_not_by_3(int input) throws OutOfRangeException {
        is(input, "Buzz");
    }
    private static Stream<Arguments> divisible_by_5_but_not_by_3() {
        return Stream.of(
                Arguments.of(5),
                Arguments.of(5 * 2),
                Arguments.of(5 * 4),
                Arguments.of(5 * 5),
                Arguments.of(5 * 7),
                Arguments.of(5 * 8),
                Arguments.of(5 * 10),
                Arguments.of(5 * 17)
        );
    }

    @ParameterizedTest
    @MethodSource
    void divisible_by_3_but_not_by_5(int input) throws OutOfRangeException {
        is(input, "Fizz");
    }
    private static Stream<Arguments> divisible_by_3_but_not_by_5() {
        return Stream.of(
                Arguments.of(3),
                Arguments.of(3 * 2),
                Arguments.of(3 * 3),
                Arguments.of(3 * 4),
                Arguments.of(3 * 6),
                Arguments.of(3 * 7),
                Arguments.of(3 * 33),
                Arguments.of(3 * 22)
        );
    }

    @ParameterizedTest
    @MethodSource
    void divisible_neither_by_3_nor_by_5(int input) throws OutOfRangeException {
        is(input, String.valueOf(input));
    }
    private static Stream<Arguments> divisible_neither_by_3_nor_by_5() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(67),
                Arguments.of(82)
        );
    }

    @ParameterizedTest
    @MethodSource
    void out_of_range_fails(int input) {
        assertThatThrownBy(() -> FizzBuzz.convert(input))
                .isInstanceOf(OutOfRangeException.class);
    }
    private static Stream<Arguments> out_of_range_fails() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(0),
                Arguments.of(101)
        );
    }

    private static void is(int input, String expected) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(input)).isEqualTo(expected);
    }
}