package ru.pasha__kun.windowfx

import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.fxml.FXMLLoader

class WindowsLoader(private val container: WindowsContainer) {
    val baseProperty: StringProperty = SimpleStringProperty(this, "baseProperty", null)
    var base: String? by baseProperty

    val baseClassProperty: Property<Class<*>> = SimpleObjectProperty(this, "baseClassProperty", WindowsLoader::class.java)
    var baseClass: Class<*> by baseClassProperty

    var loaderHook: ((String) -> Window)? = null

    fun loadFxml(name: String){
        val win = if (loaderHook != null){
            loaderHook?.invoke(name)
        } else {
            val fxmlPath = if (base == null) name else base + name
            val loader = FXMLLoader(baseClass.getResource("$fxmlPath.fxml"))
            loader.load<Window>()
        }

        win?.let { load(it) }
    }

    fun load(win: Window){
        win.init(container)
        container.windows.add(win)
    }
}