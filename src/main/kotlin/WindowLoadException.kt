package ru.pasha__kun.windowfx

class WindowLoadException : Exception {
    constructor(msg: String?) : super(msg)
    constructor(cause: Throwable?) : super(cause)
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
}