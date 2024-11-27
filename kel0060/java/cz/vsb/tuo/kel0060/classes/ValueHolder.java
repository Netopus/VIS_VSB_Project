package cz.vsb.tuo.kel0060.classes;


import java.util.function.Supplier;

/**
 * A generic Value Holder for lazy-loading objects.
 */
public class ValueHolder<T> {
    private T value;
    private boolean isLoaded = false;
    private Supplier<T> loader;

    public ValueHolder(Supplier<T> loader) {
        this.loader = loader;
    }

    public T getValue() {
        if (!isLoaded) {
            value = loader.get(); // Load the value when first accessed
            isLoaded = true;
        }
        return value;
    }
}
