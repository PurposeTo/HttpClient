package types;

import utils.StringUtils;

public class Numeric {
    private final String value;

    private Numeric(String value) {
        StringUtils.requiredNonBlank(value);
        this.value = value;
    }

    public static Numeric parse(String str) {
        StringUtils.requiredNonBlank(str);
        String numbersStr = executeNumbers(str);
        StringUtils.requiredNonBlank(numbersStr);
        return new Numeric(numbersStr);
    }

    @Override
    public String toString() {
        return value;
    }

    private static String executeNumbers(String value) {
        return value.replaceAll("[^0-9]", "");
    }
}
