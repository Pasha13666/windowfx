package ru.pasha__kun.windowfx.impl;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import ru.pasha__kun.windowfx.Window;

class MoveEventHandler implements EventHandler<MouseEvent> {
    private final Window window;
    private MouseEvent start;

    MoveEventHandler(Window window) {this.window = window;}

    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<? extends MouseEvent> type = mouseEvent.getEventType();

        if (type == MouseEvent.MOUSE_PRESSED){
            start = mouseEvent;

        } else if (type == MouseEvent.MOUSE_RELEASED || type == MouseEvent.MOUSE_DRAGGED) {
            if (start != null && window.isMovable()) {
                window.setX(window.getX() - start.getX() + mouseEvent.getX());
                window.setY(window.getY() - start.getY() + mouseEvent.getY());
            }

            if (type == MouseEvent.MOUSE_RELEASED) start = null;
        }
    }
}
