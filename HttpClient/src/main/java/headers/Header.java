package headers;


import lombok.NonNull;
import utils.Copyable;
import utils.NameValuePair;
import utils.StringUtils;

import java.util.*;

/**
 * Http message header
 */
public class Header implements NameValuePair<String, String>, Copyable<Header> {

    private static final String DEFAULT_SEPARATOR = "; ";
    @NonNull
    private String valuesSeparator = DEFAULT_SEPARATOR;
    @NonNull
    private final String name;
    @NonNull
    private final Set<String> values = new HashSet<>();

    public Header(String name) {
        this(name, new HashSet<>());
    }

    public Header(String name, String value) {
        this.name = name;
        addValue(value);
    }

    public Header(String name, Set<String> values) {
        this.name = name;
        addValues(values);
    }

    public Header setValuesSeparator(String separator) {
        StringUtils.requiredNonBlank(separator);
        final String value = getValue();
        this.valuesSeparator = separator;
        setValue(value);
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return String.join(valuesSeparator, values);
    }

    public Set<String> getValues() {
        return new HashSet<>(values);
    }

    /**
     * Объединить заголовки с одинаковыми именами в один.
     * valuesSeparator будет взят из первого заголовка.
     */
    public Header merge(@NonNull Header other) {
        String thisName = this.getName().toLowerCase();
        String otherName = other.getName().toLowerCase();

        if (!thisName.equals(otherName)) {
            throw new IllegalStateException(String.format("You can't merge headers with name '%s' and '%s'", thisName, otherName));
        }

        Set<String> values = this.getValues();
        values.addAll(other.getValues());
        return new Header(this.name, values)
                .setValuesSeparator(this.valuesSeparator);
    }

    public static Header merge(@NonNull List<Header> headers) {
        if (headers.isEmpty()) {
            throw new NoSuchElementException();
        }

        Header header = headers.get(0);
        headers.remove(header);
        headers.forEach(header::merge);
        return header;
    }

    public Header clear() {
        values.clear();
        return this;
    }

    public Header setValue(String value) {
        StringUtils.requiredNonBlank(value);
        return this.clear()
                .addValue(value);
    }

    public Header addValue(String value) {
        if (StringUtils.isBlank(value)) {
            return this;
        }

        List<String> separatedValues = StringUtils.slit(value, this.valuesSeparator);
        this.values.addAll(separatedValues);
        return this;
    }

    public Header addValues(@NonNull Set<String> values) {
        if (values.isEmpty()) {
            return this;
        }

        List<String> separatedValues = StringUtils.slit(new ArrayList<>(values), this.valuesSeparator);
        this.values.addAll(separatedValues);
        return this;
    }

    public Header removeValue(String value) {
        List<String> separatedValues = StringUtils.slit(value, this.valuesSeparator);
        separatedValues.forEach(this.values::remove);
        return this;
    }

    public Header removeValues(@NonNull Set<String> values) {
        List<String> separatedValues = StringUtils.slit(new ArrayList<>(values), this.valuesSeparator);
        separatedValues.forEach(this.values::remove);
        return this;
    }

    @Override
    public Header copy() {
        return new Header(this.name, this.values)
                .setValuesSeparator(this.valuesSeparator);
    }

    @Override
    public String toString() {
        return getName() + ": " + getValue();
    }
}
