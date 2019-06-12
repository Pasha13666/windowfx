package ru.pasha__kun.windowfx.loader;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.pasha__kun.windowfx.Window;
import ru.pasha__kun.windowfx.WindowLoadException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectWindowLoader implements WindowLoader {
    private final StringProperty base = new SimpleStringProperty(this, "base");

    @Override
    public Window load(String name, Object[] args, boolean reload) throws WindowLoadException {
        String className = base.get();
        if (className == null) className = toClassName(name);
        else className += toClassName(name);

        try {
            Class<?> c = Class.forName(className);
            Window w = null;
            Constructor<?>[] cc = c.getConstructors();
            outer:
            for (Constructor<?> co : cc){
                Class<?>[] ct = co.getParameterTypes();
                if (ct.length != args.length)
                    continue;

                for (int i = 0; i < args.length; i++)
                    if (args[i] != null && !ct[i].isAssignableFrom(args[i].getClass()))
                        continue outer;

                w = (Window) co.newInstance(args);
                break;
            }

            if (w == null)
                throw new NoSuchMethodException(argTypesToString(c, args));

            return w;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new WindowLoadException(e);

        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) throw (RuntimeException) t;
            if (t instanceof WindowLoadException) throw (WindowLoadException) t;
            if (t instanceof Error) throw (Error) t;
            throw new WindowLoadException(t);
        }
    }

    private String argTypesToString(Class<?> c, Object[] args) {
        StringBuilder b = new StringBuilder(c.getName()).append(".<init>(");

        for (int i = 0; i < args.length; i++) {
            b.append(args[i] == null? "null": args[i].getClass());

            if (i + 1 != args.length)
                b.append(", ");
        }

        return b.append(")").toString();
    }

    public String getBase() {
        return base.get();
    }

    public StringProperty baseProperty() {
        return base;
    }

    public void setBase(String base) {
        this.base.set(base);
    }
}
