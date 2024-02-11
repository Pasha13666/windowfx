package ru.pasha__kun.windowfx

import javafx.beans.value.ObservableDoubleValue
import javafx.beans.value.ObservableValue
import javafx.beans.value.WritableValue
import kotlin.reflect.KProperty


operator fun<T> ObservableValue<T>.getValue(bean: Any?, prop: KProperty<*>): T = this.value
operator fun<T> WritableValue<T>.setValue(bean: Any?, prop: KProperty<*>, value: T) {
    this.value = value
}

operator fun ObservableDoubleValue.getValue(bean: Any?, prop: KProperty<*>): Double = this.get()
