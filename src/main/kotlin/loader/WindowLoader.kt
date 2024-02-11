package ru.pasha__kun.windowfx.loader

import ru.pasha__kun.windowfx.Window
import ru.pasha__kun.windowfx.WindowLoadException

abstract class WindowLoader {
    @Throws(WindowLoadException::class)
    abstract fun load(name: String?, args: Array<out Any?>?, reload: Boolean): Window

    fun toClassName(name: String): String {
        val b = StringBuilder()
        var nu = true
        for (c in name.toCharArray()) {
            when {
                nu -> {
                    b.append(Character.toUpperCase(c))
                    nu = false
                }
                c == '_' -> nu = true
                else -> b.append(c)
            }
        }
        var name = b.toString()
        if (!name.endsWith("Window")) name += "Window"
        return name
    }

    fun toResourceName(name: String): String {
        val b = StringBuilder()
        for (c in name.removeSuffix("Window").toCharArray())
            if (Character.isUpperCase(c)) b.append('_').append(Character.toLowerCase(c)) else b.append(c)
        return b.substring(1)
    }
}