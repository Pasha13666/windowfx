package ru.pasha__kun.windowfx.loader;

import ru.pasha__kun.windowfx.Window;
import ru.pasha__kun.windowfx.WindowLoadException;

public interface WindowLoader {
    Window load(String name, Object[] args, boolean reload) throws WindowLoadException;

    default String toClassName(String name){
        StringBuilder b = new StringBuilder();
        boolean nu = true;

        for (char c : name.toCharArray()) {
            if (nu) {
                b.append(Character.toUpperCase(c));
                nu = false;
            } else if (c == '_') nu = true;
            else b.append(c);
        }

        name = b.toString();
        if (!name.endsWith("Window")) name += "Window";
        return name;
    }

    default String toResourceName(String name){
        if (name.endsWith("Window")) name = name.substring(0, name.length() - 6);

        StringBuilder b = new StringBuilder();
        for (char c : name.toCharArray())
            if (Character.isUpperCase(c)) b.append('_').append(Character.toLowerCase(c));
            else b.append(c);
        return b.substring(1);
    }
}
