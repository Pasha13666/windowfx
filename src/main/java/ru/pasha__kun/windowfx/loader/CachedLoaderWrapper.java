package ru.pasha__kun.windowfx.loader;

import ru.pasha__kun.windowfx.Window;
import ru.pasha__kun.windowfx.WindowLoadException;

public class CachedLoaderWrapper extends CachedWindowLoader {
    private final WindowLoader loader;

    public CachedLoaderWrapper(WindowLoader loader) {
        this.loader = loader;
    }

    @Override
    protected Window loadImpl(String name, Object[] args, boolean reload) throws WindowLoadException {
        return loader.load(name, args, reload);
    }

    public WindowLoader getLoader() {
        return loader;
    }
}
