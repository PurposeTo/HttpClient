package headers;

import lombok.NonNull;
import utils.Copyable;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Http message headers
 */
public class Headers implements Copyable<Headers>, Iterable<Header> {
    private final List<Header> headersList;

    public Headers() {
        this(new ArrayList<>());
    }

    public Headers(List<Header> headers) {
        this.headersList = headers.stream()
                .peek(Header::copy)
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return headersList.isEmpty();
    }

    /**
     * Найти заголовки с указанным именем
     */
    public List<Header> find(String name) {
        StringUtils.requiredNonBlank(name);
        return headersList.stream()
                .filter(header -> header.getName().equals(name))
                .collect(Collectors.toList());
    }

    /**
     * Найти заголовок с указанным именем.
     * Если несколько заголовков с указанными именами, они будут объеденены в один.
     */
    public Header findMerged(String name) {
        StringUtils.requiredNonBlank(name);
        List<Header> headers = find(name);
        return Header.merge(headers);
    }

    public String findAsValue(String name) {
        return findMerged(name).getValue();
    }

    /**
     * Проверить существование заголовка с указанным именем
     */
    public boolean contains(String name) {
        StringUtils.requiredNonBlank(name);
        return !find(name).isEmpty();
    }

    /**
     * Удалить все заголовки с указанным именем
     */
    public Headers remove(String name) {
        StringUtils.requiredNonBlank(name);
        List<Header> headers = find(name);
        headers.forEach(headersList::remove);
        return this;
    }

    /**
     * Добавить заголовок.
     * Метод игнорирует существование заголовков с такими же именами.
     */
    public Headers addAsNew(String name, @NonNull String value) {
        Header header = new Header(name, value);
        addAsNew(header);
        return this;
    }

    public Headers addAsNew(@NonNull Header header) {
        this.headersList.add(header);
        return this;
    }

    public Headers add(@NonNull Header header) {
        List<Header> headers = find(header.getName());
        if (headers.isEmpty()) {
            addAsNew(header);
        } else {
            headers.get(0).addValue(header.getValue());
        }
        return this;
    }

    /**
     * Добавить значение к заголовоку с указанным именем.
     * Если заголовок с указанным именем не найден, то создать новый.
     */
    public Headers add(String name, @NonNull String value) {
        Header header = new Header(name, value);
        add(header);
        return this;
    }

    /**
     * Удалить заголовок с указанным именем и установить новый заголовок, с указанным значением.
     */
    public Headers set(String name, @NonNull String value) {
        StringUtils.requiredNonBlank(name);
        remove(name);
        add(name, value);
        return this;
    }

    @Override
    public Headers copy() {
        return new Headers(this.headersList);
    }

    @Override
    public String toString() {
        return this.headersList.stream()
                .map(Header::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Iterator<Header> iterator() {
        return this.headersList.iterator();
    }

    @Override
    public void forEach(Consumer<? super Header> action) {
        this.headersList.forEach(action);
    }

    @Override
    public Spliterator<Header> spliterator() {
        return this.headersList.spliterator();
    }
}
