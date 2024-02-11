package ru.pasha__kun.windowfx.impl

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import ru.pasha__kun.windowfx.Window

internal class MoveEventHandler(private val window: Window) : EventHandler<MouseEvent> {
    private var start: MouseEvent? = null
    override fun handle(mouseEvent: MouseEvent) {
        val type = mouseEvent.eventType
        if (type == MouseEvent.MOUSE_PRESSED) {
            start = mouseEvent
        } else if (type == MouseEvent.MOUSE_RELEASED || type == MouseEvent.MOUSE_DRAGGED) {
            if (start != null && window.movable) {
                window.x = window.x - start!!.x + mouseEvent.x
                window.y = window.y - start!!.y + mouseEvent.y
            }
            if (type == MouseEvent.MOUSE_RELEASED) start = null
        }
    }
}