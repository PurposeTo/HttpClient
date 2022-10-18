package types;

import utils.StringUtils;

import javax.validation.constraints.NotBlank;


public class Numeric {
    private final String value;

    private Numeric(@NotBlank String value) {
        this.value = value;
    }

    public static Numeric parse(@NotBlank String str) {
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
