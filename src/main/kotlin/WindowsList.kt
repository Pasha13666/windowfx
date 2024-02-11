package ru.pasha__kun.windowfx

import com.sun.javafx.collections.NonIterableChange
import com.sun.javafx.collections.SortHelper
import javafx.collections.ModifiableObservableListBase
import javafx.collections.ObservableList
import java.util.*
import kotlin.collections.ArrayList

class WindowsList : ModifiableObservableListBase<Window>(), ObservableList<Window>, RandomAccess {
    private val backingList: MutableList<Window> = ArrayList()

    override fun get(index: Int): Window {
        return backingList[index]
    }

    override val size
    get() = backingList.size

    override fun doAdd(index: Int, element: Window) {
        backingList.add(index, element)
    }

    override fun doSet(index: Int, element: Window): Window {
        return backingList.set(index, element)
    }

    override fun doRemove(index: Int): Window {
        return backingList.removeAt(index)
    }

    override fun indexOf(element: Window): Int {
        return backingList.indexOf(element)
    }

    override fun lastIndexOf(element: Window): Int {
        return backingList.lastIndexOf(element)
    }

    override fun contains(element: Window): Boolean {
        return backingList.contains(element)
    }

    override fun containsAll(elements: Collection<Window>): Boolean {
        return backingList.containsAll(elements)
    }

    fun setFocused(window: Window){
        val index = indexOf(window)
        if (index == lastIndex) return

        setFocused(index)

        for (i in this)
            if (i != window)
                i.focused = false
    }

    private fun setFocused(index: Int){
        if (index == lastIndex) return

        if (hasListeners()) {
            beginChange()
            nextPermutation(index, size, intArrayOf(lastIndex))
        }

        val t = backingList[lastIndex]
        backingList[lastIndex] = backingList[index]
        backingList[index] = t

        if (hasListeners()) {
            endChange()
        }
    }

    override fun clear() {
        if (hasListeners()) {
            beginChange()
            nextRemove(0, this)
        }
        backingList.clear()
        ++modCount
        if (hasListeners()) {
            endChange()
        }
    }

    override fun remove(fromIndex: Int, toIndex: Int) {
        beginChange()
        for (i in fromIndex until toIndex) {
            removeAt(fromIndex)
        }
        endChange()
    }

    override fun removeAll(elements: Collection<Window>): Boolean {
        beginChange()
        val bs = BitSet(elements.size)
        for (i in 0 until size) {
            if (elements.contains(get(i))) {
                bs.set(i)
            }
        }
        if (!bs.isEmpty) {
            var cur = size
            while (bs.previousSetBit(cur - 1).also { cur = it } >= 0) {
                removeAt(cur)
            }
        }
        endChange()
        return !bs.isEmpty
    }

    override fun retainAll(elements: Collection<Window>): Boolean {
        beginChange()
        val bs = BitSet(elements.size)
        for (i in 0 until size) {
            if (!elements.contains(get(i))) {
                bs.set(i)
            }
        }
        if (!bs.isEmpty) {
            var cur = size
            while (bs.previousSetBit(cur - 1).also { cur = it } >= 0) {
                removeAt(cur)
            }
        }
        endChange()
        return !bs.isEmpty
    }

    private val helper by lazy {
        SortHelper()
    }

    override fun sort(comparator: Comparator<in Window>) {
        if (backingList.isEmpty()) {
            return
        }
        val perm = helper.sort(backingList, comparator)
        fireChange(NonIterableChange.SimplePermutationChange(0, size, perm, this))
    }
}
