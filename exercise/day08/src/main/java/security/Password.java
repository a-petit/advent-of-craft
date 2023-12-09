package security;

public class Password {
    private Password() {
    }

    public static boolean of(String value) {
        if (value.length() < 8) return false;
        if (value.toLowerCase().equals(value)) return false;
        if (value.toUpperCase().equals(value)) return false;
        if (!value.matches(".*[0-9].*")) return false;
        if (!value.matches(".*[.*#@$%&].*")) return false;
        if (!value.matches("[.*#@$%&a-zA-Z0-9]*")) return false;
        return true;
    }
}
