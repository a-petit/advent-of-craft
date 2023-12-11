package functional;

import io.vavr.control.Try;

public class Password {
    public static Try<Password> of(String password) {
        if (password.length() < 8)
            return Try.failure(new LessThanEightCharactersException());
        if (password.toLowerCase().equals(password))
            return Try.failure(new PasswordWithoutUpperCaseException());
        if (password.toUpperCase().equals(password))
            return Try.failure(new PasswordWithoutLowerCaseException());
        if (!password.matches(".*[0-9].*"))
            return Try.failure(new PasswordWithoutNumberException());
        if (!password.matches(".*[.*#@$%&].*"))
            return Try.failure(new PasswordWithoutSpecialCharacterException());
        if (!password.matches("[.*#@$%&a-zA-Z0-9]*"))
            return Try.failure(new PasswordWithoutUnauthorizedCharacterException());

        return Try.success(new Password());
    }
}
