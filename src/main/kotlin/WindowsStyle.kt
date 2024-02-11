package ru.pasha__kun.windowfx

import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.Bloom
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import ru.pasha__kun.windowfx.impl.MoveEventHandler

class WindowsStyle(private val container: WindowsContainer) {
    fun constructWindow(window: Window) = makeWindowNode(window)

    fun makeWindowHeader(window: Window): Node {
        val title = Label()
        title.textProperty().bind(window.titleProperty)
        title.effect = Bloom(0.1)
        val icon = ImageView()
        icon.imageProperty().bind(window.iconProperty)
        icon.fitWidth = 32.0
        icon.fitHeight = 32.0
        val left = HBox(icon, title)
        left.styleClass.add("window-panel-left")
        val iconify = Button()
        iconify.styleClass.addAll("window-action-button", "window-action-iconify")
        iconify.isDisable = true
        iconify.onMouseClicked = EventHandler {
            window.height = 32.0
            window.x = 0.0
            window.y = 0.0
            window.width = title.width + 5 * 32
        }
        val fullscreen = Button()
        fullscreen.styleClass.addAll("window-action-button", "window-action-fullscreen")
        fullscreen.disableProperty().bind(window.resizableProperty.not())
        fullscreen.onMouseClicked = EventHandler {
            window.x = 0.0
            window.y = 0.0
            window.height = 32.0
            window.width = title.width + 5 * 32
        }
        val help = Button()
        help.styleClass.addAll("window-action-button", "window-action-help")
        help.disableProperty().bind(window.onHelpProperty.isNull)
        help.onMouseClicked = EventHandler {
            val h = window.onHelp
            h?.run()
        }
        val close = Button()
        close.styleClass.addAll("window-action-button", "window-action-close")
        close.onMouseClicked = EventHandler {
            val c = window.onClose
            if (c == null || c.asBoolean) container.windows.remove(window)
        }
        val right = HBox(iconify, fullscreen, help, close)
        right.styleClass.add("window-panel-right")
        val panel = BorderPane()
        panel.styleClass.add("window-panel")
        panel.left = left
        panel.right = right
        val x = MoveEventHandler(window)
        panel.onMouseDragged = x
        panel.onMousePressed = x
        panel.onMouseReleased = x
        return panel
    }

    fun makeResizePane(window: Window, type: String, id: Int, cursor: Cursor): Pane {
        val resize = Pane()
//        val e = ResizeEventHandler(window, id)
        resize.styleClass.addAll("window-resize", "window-resize-$type")
//        resize.onMouseDragged = e
//        resize.onMousePressed = e
//        resize.onMouseReleased = e
        resize.cursor = cursor // Cursor.cursor("${res}_RESIZE")
        if (id and 1 != 0) {
            resize.maxHeight = 5.0
            resize.minHeight = 5.0
            resize.prefHeight = 5.0
        }
        if (id and 2 != 0) {
            resize.maxWidth = 5.0
            resize.minWidth = 5.0
            resize.prefWidth = 5.0
        }
        return resize
    }

    fun makeWindowNode(window: Window): Node {
        val data = VBox(window.content)
        data.styleClass.add("window-content")
        data.minWidthProperty().bind(window.minWidthProperty)
        data.minHeightProperty().bind(window.minHeightProperty)
        data.prefWidthProperty().bind(window.widthProperty)
        data.prefHeightProperty().bind(window.heightProperty)
        data.maxWidthProperty().bind(window.maxWidthProperty)
        data.maxHeightProperty().bind(window.maxHeightProperty)

        val resizeTop = makeResizePane(window, "top", 9, Cursor.N_RESIZE)
        val resizeTopLeft = makeResizePane(window, "top-left", 15, Cursor.NW_RESIZE)
        val resizeTopRight = makeResizePane(window, "top-right", 11, Cursor.NE_RESIZE)
        val resizeLeft = makeResizePane(window, "left", 6, Cursor.W_RESIZE)
        val resizeRight = makeResizePane(window, "right", 2, Cursor.E_RESIZE)
        val resizeBottom = makeResizePane(window, "bottom", 1, Cursor.S_RESIZE)
        val resizeBottomLeft = makeResizePane(window, "bottom-left", 7, Cursor.SW_RESIZE)
        val resizeBottomRight = makeResizePane(window, "bottom-right", 3, Cursor.SE_RESIZE)
        val content = GridPane()
        content.addRow(0, resizeTopLeft, resizeTop, resizeTopRight)
        content.addRow(1, resizeLeft, data, resizeRight)
        content.addRow(2, resizeBottomLeft, resizeBottom, resizeBottomRight)
        content.styleClass.add("window-content-wrapper")
        val panel = makeWindowHeader(window)
        val root = VBox(panel, content)
        root.styleClass.add("window-root")
        root.layoutXProperty().bind(window.xProperty)
        root.layoutYProperty().bind(window.yProperty)
        root.onMousePressed = EventHandler {
            window.focused = true
        }
        return root
    }
}