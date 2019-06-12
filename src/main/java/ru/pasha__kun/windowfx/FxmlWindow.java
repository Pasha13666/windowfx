package ru.pasha__kun.windowfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import ru.pasha__kun.windowfx.loader.WindowLoader;

import java.io.IOException;
import java.net.URL;

public class FxmlWindow extends Window {
    private final ObjectProperty<FXMLLoader> loader = new SimpleObjectProperty<>(this, "loader");
    private final ObjectProperty<URL> base = new SimpleObjectProperty<>(this, "base");

    @Override
    public void onLoad(WindowContainer container) throws WindowLoadException {
        super.onLoad(container);
        WindowLoader l = container.getWindowLoader();
        if (l == null) throw new IllegalStateException("FxmlWindow must be added with loader!");

        try {
            FXMLLoader loader = this.loader.get();
            loader.setLocation(new URL(base.get(), l.toResourceName(getClass().getSimpleName()) + ".fxml"));
            setContent(loader.load());
        } catch (IOException e){
            throw new WindowLoadException(e);
        }
    }

    public FXMLLoader getLoader() {
        return loader.get();
    }

    public ObjectProperty<FXMLLoader> loaderProperty() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader.set(loader);
    }
}
