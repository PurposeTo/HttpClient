package utils;

import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
    public static String DEFAULT_REPLACEMENT_RX = "\\$\\{([\\w\\s]+)}";

    public static boolean containsPattern(@NonNull String source, String rx) {
        requiredNonNullOrEmpty(rx);

        Pattern p = Pattern.compile(rx);
        Matcher m = p.matcher(source);
        return m.find();
    }

    public static String parametrize(@NonNull String source, @NonNull Map<String, String> replacements) {
        return parametrize(DEFAULT_REPLACEMENT_RX, source, replacements);
    }

    public static String parametrize(@NonNull String regexPattern,
                                     @NonNull String source,
                                     @NonNull Map<String, String> replacements) {
        requiredNonNullOrEmpty(regexPattern);

        StringBuilder sb = new StringBuilder(); //use StringBuffer before Java 9
        Pattern p = Pattern.compile(regexPattern);
        Matcher m = p.matcher(source);

        while (m.find()) {
            // Avoids throwing a NullPointerException in the case that you
            // Don't have a replacement defined in the map for the match
            String repKey = m.group(1);
            String repString = replacements.get(repKey);
            if (repString != null)
                m.appendReplacement(sb, repString);
        }
        m.appendTail(sb);

        return sb.toString();
    }

    public static boolean isNullOrEmpty(String str) {
        return Objects.isNull(str) || str.trim().equals("");
    }

    public static boolean isNonNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static String requiredNonNullOrEmpty(String str) {
        if (isNullOrEmpty(str)) {
            throw new NullPointerException();
        }

        return str;
    }

    /**
     * Разделить каждый элемент коллекции по valuesSeparator, если требуется
     */
    public static List<String> slit(@NonNull List<String> values, String valuesSeparator) {
        requiredNonNullOrEmpty(valuesSeparator);
        if (values.isEmpty()) {
            return values;
        }

        return values.stream()
                .map(str ->
                {
                    if (str.contains(valuesSeparator)) {
                        return slit(str, valuesSeparator);
                    } else {
                        return List.of(str);
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<String> slit(String value, String valuesSeparator) {
        return Arrays.stream(value.split(valuesSeparator)).collect(Collectors.toList());
    }

    public static String toPretty(@NonNull Map<String, String> map) {
        return map.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n"));
    }
}
