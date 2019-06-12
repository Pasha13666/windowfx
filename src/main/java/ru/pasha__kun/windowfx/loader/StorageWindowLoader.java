package ru.pasha__kun.windowfx.loader;

import ru.pasha__kun.windowfx.Window;
import ru.pasha__kun.windowfx.WindowContainer;
import ru.pasha__kun.windowfx.WindowLoadException;

public class StorageWindowLoader extends CachedWindowLoader {

    @Override
    protected Window loadImpl(String name, Object[] args, boolean reload) {
        throw new IllegalArgumentException("Unknown window " + name);
    }

    public void put(WindowContainer container, String name, Window win) throws WindowLoadException {
        win.onLoad(container);
        putToCache(name, win);
    }
}
