package ru.pasha__kun.windowfx

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ListChangeListener
import javafx.scene.layout.Region

class WindowsContainer : Region() {
    val loaderProperty: ObjectProperty<WindowsLoader> = SimpleObjectProperty(this, "loaderProperty", WindowsLoader(this))
    val loader by loaderProperty

    val styleProperty: ObjectProperty<WindowsStyle> = SimpleObjectProperty(this, "styleProperty", WindowsStyle(this))
    val style by styleProperty

    val windows: WindowsList = WindowsList()

    init {
        windows.addListener { it: ListChangeListener.Change<out Window> ->
            while (it.next()){
                if (it.wasPermutated()){
                    for (i in it.from until it.to)
                        children.add(it.getPermutation(i), children.removeAt(i))

                    continue
                }

                if (it.wasRemoved())
                    children.remove(it.from, it.from + it.removedSize)

                if (it.wasAdded())
                    for (i in it.from until it.to) {
                        children.add(style.constructWindow(windows[i]))
                    }
            }
        }
    }
}
