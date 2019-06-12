package ru.pasha__kun.windowfx.impl;

import javafx.scene.Node;
import ru.pasha__kun.windowfx.Window;

public class WindowEntry {
    private final Window window;
    private Node root;

    public WindowEntry(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
