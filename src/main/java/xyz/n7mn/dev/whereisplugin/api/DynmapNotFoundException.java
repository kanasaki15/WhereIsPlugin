package xyz.n7mn.dev.whereisplugin.api;

@Deprecated
public class DynmapNotFoundException extends Exception {
    private final String message = "Dynmap is not This Server";

    public DynmapNotFoundException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
