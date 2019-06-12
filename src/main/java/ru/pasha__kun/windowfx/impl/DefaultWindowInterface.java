package ru.pasha__kun.windowfx.impl;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import ru.pasha__kun.windowfx.Window;
import ru.pasha__kun.windowfx.WindowContainer;
import ru.pasha__kun.windowfx.WindowInterface;

import java.util.function.BooleanSupplier;

public final class DefaultWindowInterface implements WindowInterface {
    private final ObservableList<Window> windows;

    private DefaultWindowInterface(WindowContainer container) {
        this.windows = container.getWindows();
    }

    public static DefaultWindowInterface makeFor(WindowContainer container) {
        if (container == null) throw new NullPointerException("container");
        container.getStylesheets().add("/ru/pasha__kun/windowfx/impl/style.css");
        return new DefaultWindowInterface(container);
    }

    private Pane makeResizePane(Window window, String type, int id, Cursor cursor){
        Pane resize = new Pane();
        ResizeEventHandler e = new ResizeEventHandler(window, id);
        resize.getStyleClass().addAll("window-resize", "window-resize-" + type);

        resize.setOnMouseDragged(e);
        resize.setOnMousePressed(e);
        resize.setOnMouseReleased(e);
        resize.setCursor(cursor);

        if ((id & 1) != 0) {
            resize.setMaxHeight(5);
            resize.setMinHeight(5);
            resize.setPrefHeight(5);
        }

        if ((id & 2) != 0) {
            resize.setMaxWidth(5);
            resize.setMinWidth(5);
            resize.setPrefWidth(5);
        }

        return resize;
    }

    @Override
    public Node makeWindowRoot(Window window) {
        Label title = new Label();
        title.textProperty().bind(window.titleProperty());
        title.setEffect(new Bloom(0.1));

        ImageView icon = new ImageView();
        icon.imageProperty().bind(window.iconProperty());
        icon.setFitWidth(32);
        icon.setFitHeight(32);

        HBox left = new HBox(icon, title);
        left.getStyleClass().add("window-panel-left");

        Button iconify = new Button();
        iconify.getStyleClass().addAll("window-action-button", "window-action-iconify");
        iconify.setDisable(true);
        iconify.setOnMouseClicked(e -> {
            window.setHeight(32);
            window.setX(0);
            window.setY(0);
            window.setWidth(title.getWidth() + 5 * 32);
        });


        Button fullscreen = new Button();
        fullscreen.getStyleClass().addAll("window-action-button", "window-action-fullscreen");
        fullscreen.disableProperty().bind(window.resizableProperty().not());
        fullscreen.setOnMouseClicked(e -> {
            window.setX(0);
            window.setY(0);
            window.setHeight(32);
            window.setWidth(title.getWidth() + 5 * 32);
        });


        Button help = new Button();
        help.getStyleClass().addAll("window-action-button", "window-action-help");
        help.disableProperty().bind(window.onHelpProperty().isNull());
        help.setOnMouseClicked(e -> {
            Runnable h = window.getOnHelp();
            if (h != null) h.run();
        });

        Button close = new Button();
        close.getStyleClass().addAll("window-action-button", "window-action-close");
        close.setOnMouseClicked(e -> {
            BooleanSupplier c = window.getOnClose();
            if (c == null || c.getAsBoolean()) windows.remove(window);
        });

        HBox right = new HBox(iconify, fullscreen, help, close);
        right.getStyleClass().add("window-panel-right");

        BorderPane panel = new BorderPane();
        panel.getStyleClass().add("window-panel");
        panel.setLeft(left);
        panel.setRight(right);

        MoveEventHandler x = new MoveEventHandler(window);
        panel.setOnMouseDragged(x);
        panel.setOnMousePressed(x);
        panel.setOnMouseReleased(x);


        VBox data = new VBox(window.getContent());
        data.getStyleClass().add("window-content");
        data.minWidthProperty().bind(window.minWidthProperty());
        data.minHeightProperty().bind(window.minHeightProperty());
        data.prefWidthProperty().bind(window.widthProperty());
        data.prefHeightProperty().bind(window.heightProperty());
        data.maxWidthProperty().bind(window.maxWidthProperty());
        data.maxHeightProperty().bind(window.maxHeightProperty());

        Pane resizeLeft = makeResizePane(window, "left", 0b110, Cursor.W_RESIZE);
        Pane resizeRight = makeResizePane(window, "right", 0b010, Cursor.E_RESIZE);
        Pane resizeBottom = makeResizePane(window, "bottom", 0b001, Cursor.S_RESIZE);
        Pane resizeBottomLeft = makeResizePane(window, "bottom-left", 0b111, Cursor.SW_RESIZE);
        Pane resizeBottomRight = makeResizePane(window, "bottom-right", 0b011, Cursor.SE_RESIZE);

        GridPane content = new GridPane();
        content.addRow(0, resizeLeft, data, resizeRight);
        content.addRow(1, resizeBottomLeft, resizeBottom, resizeBottomRight);
        content.getStyleClass().add("window-content-wrapper");

        VBox root = new VBox(panel, content);
        root.getStyleClass().add("window-root");

        root.layoutXProperty().bind(window.xProperty());
        root.layoutYProperty().bind(window.yProperty());

        root.setOnMousePressed(ev -> {
            int i = windows.indexOf(window), l = windows.size() - 1;
            if (i != l) windows.add(l, windows.remove(i));
        });
        return root;
    }
}
