package ru.pasha__kun.windowfx;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.function.BooleanSupplier;

/**
 * Represents virtual window.
 *
 * @author Pasha__kun
 */
public class Window {
    private final BooleanProperty modal = new SimpleBooleanProperty(this, "modal");
    private final BooleanProperty resizable = new SimpleBooleanProperty(this, "resizable", true);
    private final BooleanProperty movable = new SimpleBooleanProperty(this, "movable", true);
    private final DoubleProperty width = new SimpleDoubleProperty(this, "width");
    private final DoubleProperty height = new SimpleDoubleProperty(this, "height");
    private final DoubleProperty minWidth = new SimpleDoubleProperty(this, "minWidth", 0);
    private final DoubleProperty minHeight = new SimpleDoubleProperty(this, "minHeight", 0);
    private final DoubleProperty maxWidth = new SimpleDoubleProperty(this, "maxWidth", Double.MAX_VALUE);
    private final DoubleProperty maxHeight = new SimpleDoubleProperty(this, "maxHeight", Double.MAX_VALUE);
    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0);
    private final StringProperty title = new SimpleStringProperty(this, "title");
    private final ObjectProperty<Image> icon = new SimpleObjectProperty<>(this, "icon");
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");
    private final ObjectProperty<BooleanSupplier> onClose = new SimpleObjectProperty<>(this, "onClose");
    private final ObjectProperty<Runnable> onHelp = new SimpleObjectProperty<>(this, "onHelp");

    public Window(){
        width.addListener(observable -> {
            if (width.get() > maxWidth.get())
                width.set(maxWidth.get());
            if (width.get() < minWidth.get())
                width.set(minWidth.get());
        });

        height.addListener(observable -> {
            if (height.get() > maxHeight.get())
                height.set(maxHeight.get());
            if (height.get() < minHeight.get())
                height.set(minHeight.get());
        });

        x.addListener(observable -> {
            if (x.get() < 0)
                x.set(0);
        });

        y.addListener(observable -> {
            if (y.get() < 0)
                y.set(0);
        });
    }

    public void onLoad(WindowContainer container) throws WindowLoadException {}

    public void onUnload(WindowContainer container){}

    /**
     * Set maximum width and height.
     *
     * @param width Maximum width
     * @param height Maximum height
     */
    public void setMaxSize(double width, double height){
        setMaxWidth(width);
        setMaxHeight(height);
    }

    /**
     * Set minimum width and height.
     *
     * @param width Minimum width
     * @param height Minimum height
     */
    public void setMinSize(double width, double height){
        setMinWidth(width);
        setMinHeight(height);
    }

    /**
     * Set window width and height.
     *
     * @param width Window width
     * @param height Window height
     */
    public void setSize(double width, double height){
        setWidth(width);
        setHeight(height);
    }

    public BooleanSupplier getOnClose() {
        return onClose.get();
    }

    public ObjectProperty<BooleanSupplier> onCloseProperty() {
        return onClose;
    }

    public void setOnClose(BooleanSupplier onClose) {
        this.onClose.set(onClose);
    }

    public Runnable getOnHelp() {
        return onHelp.get();
    }

    public ObjectProperty<Runnable> onHelpProperty() {
        return onHelp;
    }

    public void setOnHelp(Runnable onHelp) {
        this.onHelp.set(onHelp);
    }

    public void setPosition(double x, double y){
        setX(x);
        setY(y);
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public Image getIcon() {
        return icon.get();
    }

    public ObjectProperty<Image> iconProperty() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon.set(icon);
    }

    public double getMinWidth() {
        return minWidth.get();
    }

    public DoubleProperty minWidthProperty() {
        return minWidth;
    }

    public void setMinWidth(double minWidth) {
        this.minWidth.set(minWidth);
    }

    public double getMinHeight() {
        return minHeight.get();
    }

    public DoubleProperty minHeightProperty() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight.set(minHeight);
    }

    public double getMaxWidth() {
        return maxWidth.get();
    }

    public DoubleProperty maxWidthProperty() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth.set(maxWidth);
    }

    public double getMaxHeight() {
        return maxHeight.get();
    }

    public DoubleProperty maxHeightProperty() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight.set(maxHeight);
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public boolean isModal() {
        return modal.get();
    }

    public BooleanProperty modalProperty() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal.set(modal);
    }

    public boolean isResizable() {
        return resizable.get();
    }

    public BooleanProperty resizableProperty() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable.set(resizable);
    }

    public boolean isMovable() {
        return movable.get();
    }

    public BooleanProperty movableProperty() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable.set(movable);
    }
}
