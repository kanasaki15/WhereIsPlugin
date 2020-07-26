package xyz.n7mn.dev.whereisplugin.dataSystem;

public class DataSystemResult {

    private String ErrorMessage;
    private boolean isError;

    public DataSystemResult(String ErrorMessage, boolean isErrorFlag){
        this.ErrorMessage = ErrorMessage;
        this.isError = isErrorFlag;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean isError() {
        return isError;
    }
}
