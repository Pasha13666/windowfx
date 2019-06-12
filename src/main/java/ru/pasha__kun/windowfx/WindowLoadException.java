package ru.pasha__kun.windowfx;

public class WindowLoadException extends Exception {
    public WindowLoadException(String msg){
        super(msg);
    }

    public WindowLoadException(Throwable cause){
        super(cause);
    }

    public WindowLoadException(String msg, Throwable cause){
        super(msg, cause);
    }
}
