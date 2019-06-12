package ru.pasha__kun.windowfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ru.pasha__kun.windowfx.impl.DefaultWindowInterface;
import ru.pasha__kun.windowfx.impl.WindowEntry;
import ru.pasha__kun.windowfx.impl.WindowList;
import ru.pasha__kun.windowfx.loader.WindowLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * WindowContainer contains ObservableList of {@link Window Windows}.
 *
 * @author Pasha__kun
 */
public class WindowContainer extends Pane {
    private final ObservableList<WindowEntry> entries = FXCollections.observableArrayList();

    /**
     * List of {@link Window windows} in this container.
     */
    private final ReadOnlyListProperty<Window> windows = new SimpleListProperty<>(
            this, "windows", new WindowList(entries));

    /**
     * {@link WindowInterface WindowIntrtface} used for creating javafx interface for each window in this container.
     */
    private final ObjectProperty<WindowInterface> windowInterface = new SimpleObjectProperty<>(
            this, "windowInterface", DefaultWindowInterface.makeFor(this));

    private final ObjectProperty<WindowLoader> windowLoader = new SimpleObjectProperty<>(this, "windowLoader");

    {
        entries.addListener((ListChangeListener<? super WindowEntry>) change -> {
            ObservableList<Node> children = getChildren();
            WindowInterface windowInterface = getWindowInterface();

            while (change.next()) {
                if (change.wasRemoved()) children.remove(change.getFrom(), change.getTo() + 1);

                if (change.wasAdded()) {
                    List<Node> n = new ArrayList<>(change.getAddedSize());
                    for (WindowEntry w : change.getAddedSubList()) {
                        Node node = w.getRoot();
                        if (node == null) {
                            node = windowInterface.makeWindowRoot(w.getWindow());
                            w.setRoot(node);
                        }
                        n.add(node);
                    }

                    children.addAll(change.getFrom(), n);
                }

                if (change.wasUpdated()) for (int i = change.getFrom(); i < change.getTo(); i++) {
                    WindowEntry w = entries.get(i);
                    Node node = w.getRoot();
                    if (node == null) {
                        node = windowInterface.makeWindowRoot(w.getWindow());
                        w.setRoot(node);
                    }
                    children.set(i, node);
                }
            }
        });
    }

    public Window open(String name, Object ...args) throws WindowLoadException {
        WindowLoader loader = getWindowLoader();
        if (loader == null)
            throw new IllegalStateException("Loader not set!");

        Window w = loader.load(name, args, false);
        windows.add(w);
        return w;
    }

    public Window openNew(String name, Object ...args) throws WindowLoadException {
        WindowLoader loader = getWindowLoader();
        if (loader == null)
            throw new IllegalStateException("Loader not set!");

        Window w = loader.load(name, args, true);
        windows.add(w);
        return w;
    }

    public WindowLoader getWindowLoader() {
        return windowLoader.get();
    }

    public ObjectProperty<WindowLoader> windowLoaderProperty() {
        return windowLoader;
    }

    public void setWindowLoader(WindowLoader windowLoader) {
        this.windowLoader.set(windowLoader);
    }

    public WindowInterface getWindowInterface() {
        return windowInterface.get();
    }

    public ObjectProperty<WindowInterface> windowInterfaceProperty() {
        return windowInterface;
    }

    public void setWindowInterface(WindowInterface windowInterface) {
        this.windowInterface.set(windowInterface);
    }

    public ObservableList<Window> getWindows() {
        return windows.get();
    }

    public ReadOnlyListProperty<Window> windowsProperty() {
        return windows;
    }
}
