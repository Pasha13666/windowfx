package ru.pasha__kun.windowfx

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import java.io.IOException
import java.net.URL

//open class FxmlWindow : Window() {
//    val loaderProperty: ObjectProperty<FXMLLoader> = SimpleObjectProperty(this, "loader")
//    val baseProperty: ObjectProperty<URL> = SimpleObjectProperty(this, "base")
//
//    var loader: FXMLLoader by loaderProperty
//    var base: URL by baseProperty
//
//    @Throws(WindowLoadException::class)
//    override fun onLoad(container: WindowsContainer?) {
//        super.onLoad(container)
//        val l = container!!.windowLoader ?: throw IllegalStateException("FxmlWindow must be added with loader!")
//        try {
//            val loader = loader
//            loader.location = URL(base, l.toResourceName(javaClass.simpleName) + ".fxml")
//            content = loader.load()
//        } catch (e: IOException) {
//            throw WindowLoadException(e)
//        }
//    }
//}