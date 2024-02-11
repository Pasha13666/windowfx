package ru.pasha__kun.windowfx.impl

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import ru.pasha__kun.windowfx.Window

/**
 * Direction id bits:
 * 1. horizontal
 * 2. vertical
 * 3. left (if bit 2 set)
 * 4. top (if bit 1 set)
 *
 * @param window Resizable window
 */
class ResizeEventHandlers(private val window: Window) {
    class Handlers(private val update: (MouseEvent, MouseEvent) -> Unit) {
        private var start: MouseEvent? = null

        inner class PressHandler : EventHandler<MouseEvent>{
            override fun handle(event: MouseEvent) {
                start = event
            }
        }

        inner class ReleaseHandler : EventHandler<MouseEvent>{
            override fun handle(event: MouseEvent) {
                if (start != null)
                    update(start!!, event)
                start = null
            }
        }

        inner class DragHandler : EventHandler<MouseEvent>{
            override fun handle(event: MouseEvent) {
                if (start != null)
                    update(start!!, event)
                start = event
            }
        }

        fun bind(node: Node){
            node.onMousePressed = PressHandler()
            node.onMouseReleased = ReleaseHandler()
            node.onMouseDragged = DragHandler()
        }
    }

    private fun left(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dx = start.sceneX - event.sceneX
        window.width += dx
        window.x -= dx
    }

    private fun right(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dx = start.sceneX - event.sceneX
        window.width -= dx
    }

    private fun bottom(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dy = start.sceneY - event.sceneY
        window.height -= dy
    }

    private fun top(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dy = start.sceneY - event.sceneY
        window.height += dy
        window.y -= dy
    }


    private fun topLeft(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dx = start.sceneX - event.sceneX
        val dy = start.sceneY - event.sceneY
        window.width += dx
        window.x -= dx
        window.height += dy
        window.y -= dy
    }

    private fun topRight(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dy = start.sceneY - event.sceneY
        window.width -= start.sceneX - event.sceneX
        window.height += dy
        window.y -= dy
    }

    private fun bottomLeft(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        val dx = start.sceneX - event.sceneX
        window.width += dx
        window.x -= dx
        window.height -= start.sceneY - event.sceneY
    }

    private fun bottomRight(start: MouseEvent, event: MouseEvent){
        if (!window.resizable)
            return

        window.width -= start.sceneX - event.sceneX
        window.height -= start.sceneY - event.sceneY
    }

    val map = mapOf(
        "left" to Handlers(::left),
        "right" to Handlers(::right),
        "top" to Handlers(::top),
        "bottom" to Handlers(::bottom),
        "topLeft" to Handlers(::topLeft),
        "topRight" to Handlers(::topRight),
        "bottomLeft" to Handlers(::bottomLeft),
        "bottomRight" to Handlers(::bottomRight),
    )

    fun bind(node: Node, position: String){
        map[position]!!.bind(node)
    }
}