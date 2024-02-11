package ru.pasha__kun.windowfx.loader

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import ru.pasha__kun.windowfx.Window
import ru.pasha__kun.windowfx.WindowLoadException
import ru.pasha__kun.windowfx.getValue
import ru.pasha__kun.windowfx.setValue
import java.lang.reflect.InvocationTargetException

class ReflectWindowLoader : WindowLoader() {
    val baseProperty: StringProperty = SimpleStringProperty(this, "base")
    var base: String? by baseProperty

    @Throws(WindowLoadException::class)
    override fun load(name: String?, args: Array<out Any?>?, reload: Boolean): Window {
        val className = (base ?: "") + toClassName(name!!)

        try {
            val c = Class.forName(className)
            var w: Window? = null
            outer@ for (co in c.constructors) {
                val ct = co.parameterTypes
                if (ct.size != args!!.size) continue

                for (i in args.indices)
                    if (args[i] != null && !ct[i].isAssignableFrom(args[i]!!.javaClass))
                        continue@outer

                w = co.newInstance(*args) as Window
                break
            }
            if (w == null) throw NoSuchMethodException(argTypesToString(c, args))
            return w

        } catch (e: ClassNotFoundException) {
            throw WindowLoadException(e)
        } catch (e: NoSuchMethodException) {
            throw WindowLoadException(e)
        } catch (e: InstantiationException) {
            throw WindowLoadException(e)
        } catch (e: IllegalAccessException) {
            throw WindowLoadException(e)
        } catch (e: InvocationTargetException) {
            val t = e.cause
            if (t is RuntimeException) throw (t as RuntimeException?)!!
            if (t is WindowLoadException) throw (t as WindowLoadException?)!!
            if (t is Error) throw (t as Error?)!!
            throw WindowLoadException(t)
        }
    }

    private fun argTypesToString(c: Class<*>, args: Array<out Any?>?): String {
        val b = StringBuilder(c.name).append(".<init>(")
        for (i in args!!.indices) {
            b.append(if (args[i] == null) "null" else args[i]!!.javaClass)
            if (i + 1 != args.size) b.append(", ")
        }
        return b.append(")").toString()
    }
}
