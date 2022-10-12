package request.payload.multiPart;

import headers.Header;
import utils.Copyable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class FormParts implements Iterable<FormPart>, Copyable<FormParts> {
    private final List<FormPart> parts;

    public FormParts() {
        this(new ArrayList<>());
    }

    public FormParts(List<FormPart> parts) {
        this.parts = parts;
    }

    public FormParts addPart(FormPart part) {
        this.parts.add(part);
        return this;
    }

    @Override
    public Iterator<FormPart> iterator() {
        return parts.iterator();
    }

    @Override
    public void forEach(Consumer<? super FormPart> action) {
        parts.forEach(action);
    }

    @Override
    public Spliterator<FormPart> spliterator() {
        return parts.spliterator();
    }

    @Override
    public FormParts copy() {
        return new FormParts(new ArrayList<>(this.parts));
    }
}
