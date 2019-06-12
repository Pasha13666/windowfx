package ru.pasha__kun.windowfx.loader;

import ru.pasha__kun.windowfx.Window;
import ru.pasha__kun.windowfx.WindowLoadException;
import ru.pasha__kun.windowfx.loader.WindowLoader;

import java.util.HashMap;
import java.util.Map;

public abstract class CachedWindowLoader implements WindowLoader {
    private final Map<String, Window> cache = makeCache();

    protected Map<String, Window> makeCache(){
        return new HashMap<>();
    }

    @Override
    public Window load(String name, Object[] args, boolean reload) throws WindowLoadException {
        if (args == null || args.length == 0){
            Window win = cache.get(name);
            if (reload || win == null){
                win = loadImpl(name, args, reload);
                cache.put(name, win);
            }

            return win;
        }

        return loadImpl(name, args, reload);
    }

    public void clearCache(){
        cache.clear();
    }

    public Window getCached(String name){
        return cache.get(name);
    }

    public boolean isCached(String name){
        return cache.containsKey(name);
    }

    protected void putToCache(String name, Window win){
        cache.put(name, win);
    }

    protected abstract Window loadImpl(String name, Object[] args, boolean reload) throws WindowLoadException;
}
