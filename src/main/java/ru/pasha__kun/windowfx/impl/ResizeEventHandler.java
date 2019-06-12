package ru.pasha__kun.windowfx.impl;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import ru.pasha__kun.windowfx.Window;

class ResizeEventHandler implements EventHandler<MouseEvent> {
    private final Window window;
    private final int direction;
    private MouseEvent start;

    /**
     *
     * Direction id bits:
     * 1. horizontal
     * 2. vertical
     * 3. left (if bit 2 set)
     * 4. top (if bit 1 set)
     *
     * @param window Resizable window
     * @param direction Direction id
     */
    ResizeEventHandler(Window window, int direction) {
        this.window = window;
        this.direction = direction;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<? extends MouseEvent> type = mouseEvent.getEventType();

        if (type == MouseEvent.MOUSE_PRESSED){
            start = mouseEvent;

        } else if (type == MouseEvent.MOUSE_RELEASED || type == MouseEvent.MOUSE_DRAGGED) {
            if (start != null && window.isResizable()) {
                if ((direction & 2) != 0) {
                    double d = start.getX() - mouseEvent.getX();
                    if ((direction & 4) != 0) {
                        window.setWidth(window.getWidth() + d);
                        window.setX(window.getX() - d);
                    } else window.setWidth(window.getWidth() - d);
                }
                if ((direction & 1) != 0) {
                    double d = start.getY() - mouseEvent.getY();
                    if ((direction & 8) != 0) {
                        window.setHeight(window.getHeight() + d);
                        window.setY(window.getY() - d);
                    } else window.setHeight(window.getHeight() - d);
                }
            }

            if (type == MouseEvent.MOUSE_RELEASED) start = null;
        }
    }
}
