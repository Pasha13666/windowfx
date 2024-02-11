package ru.pasha__kun.windowfx

import javafx.beans.DefaultProperty
import javafx.beans.property.*
import javafx.scene.Node
import javafx.scene.image.Image
import java.util.function.BooleanSupplier

@DefaultProperty("content")
open class Window {
    val focusedProperty: BooleanProperty = SimpleBooleanProperty(this, "focusedProperty", false)
    val modalProperty: BooleanProperty = SimpleBooleanProperty(this, "modal")
    val resizableProperty: BooleanProperty = SimpleBooleanProperty(this, "resizable", true)
    val movableProperty: BooleanProperty = SimpleBooleanProperty(this, "movable", true)
    val widthProperty: DoubleProperty = SimpleDoubleProperty(this, "width")
    val heightProperty: DoubleProperty = SimpleDoubleProperty(this, "height")
    val minWidthProperty: DoubleProperty = SimpleDoubleProperty(this, "minWidth", 0.0)
    val minHeightProperty: DoubleProperty = SimpleDoubleProperty(this, "minHeight", 0.0)
    val maxWidthProperty: DoubleProperty = SimpleDoubleProperty(this, "maxWidth", Double.MAX_VALUE)
    val maxHeightProperty: DoubleProperty = SimpleDoubleProperty(this, "maxHeight", Double.MAX_VALUE)
    val xProperty: DoubleProperty = SimpleDoubleProperty(this, "x", 0.0)
    val yProperty: DoubleProperty = SimpleDoubleProperty(this, "y", 0.0)
    val titleProperty: StringProperty = SimpleStringProperty(this, "title")
    val iconProperty: ObjectProperty<Image> = SimpleObjectProperty(this, "icon")
    val contentProperty: ObjectProperty<Node> = SimpleObjectProperty(this, "content")
    val onCloseProperty: ObjectProperty<BooleanSupplier?> = SimpleObjectProperty(this, "onClose")
    val onHelpProperty: ObjectProperty<Runnable?> = SimpleObjectProperty(this, "onHelp")

    var focused: Boolean by focusedProperty
    var modal: Boolean by modalProperty
    var resizable: Boolean by resizableProperty
    var movable: Boolean by movableProperty
    var width: Double by widthProperty
    var height: Double by heightProperty
    var minWidth: Double by minWidthProperty
    var minHeight: Double by minHeightProperty
    var maxWidth: Double by maxWidthProperty
    var maxHeight: Double by maxHeightProperty
    var x: Double by xProperty
    var y: Double by yProperty
    var title: String by titleProperty
    var icon: Image by iconProperty
    var content: Node? by contentProperty
    var onClose by onCloseProperty
    var onHelp by onHelpProperty

    private lateinit var container: WindowsContainer

    internal fun init(container: WindowsContainer){
        this.container = container
    }

    init {
        widthProperty.addListener { _ ->
            if (width > maxWidth) width = maxWidth
            else if (width < minWidth) width = minWidth
        }

        heightProperty.addListener { _ ->
            if (height > maxHeight) height = maxHeight
            else if (height < minHeight) height = minHeight
        }
        xProperty.addListener { _ ->
            if (x < 0) x = 0.0
        }
        yProperty.addListener { _ ->
            if (y < 0) y = 0.0
        }

        focusedProperty.addListener { _ ->
            if (focused) container.windows.setFocused(this)
        }
    }

    @Throws(WindowLoadException::class)
    open fun onLoad(container: WindowsContainer?) {
    }

    open fun onUnload(container: WindowsContainer?) {}

    /**
     * Set maximum width and height.
     *
     * @param width Maximum width
     * @param height Maximum height
     */
    fun setMaxSize(width: Double, height: Double) {
        maxWidth = width
        maxHeight = height
    }

    /**
     * Set minimum width and height.
     *
     * @param width Minimum width
     * @param height Minimum height
     */
    fun setMinSize(width: Double, height: Double) {
        minWidth = width
        minHeight = height
    }

    /**
     * Set window width and height.
     *
     * @param width Window width
     * @param height Window height
     */
    fun setSize(width: Double, height: Double) {
        this.width = width
        this.height = height
    }

    fun setPosition(x: Double, y: Double) {
        this.x = x
        this.y = y
    }
}
