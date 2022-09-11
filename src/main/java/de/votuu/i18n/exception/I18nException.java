package de.votuu.i18n.exception;

public class I18nException extends RuntimeException {

    public I18nException() {
        super();
    }

    public I18nException(String s) {
        super(s);
    }

    private transient int extendedMessageState;
    private transient String extendedMessage;

    public synchronized Throwable fillInStackTrace() {
        if (extendedMessageState == 0) {
            extendedMessageState = 1;
        } else if (extendedMessageState == 1) {
            extendedMessage = getExtendedNPEMessage();
            extendedMessageState = 2;
        }
        return super.fillInStackTrace();
    }

    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            synchronized(this) {
                if (extendedMessageState == 1) {
                    // Only the original stack trace was filled in. Message will
                    // compute correctly.
                    extendedMessage = getExtendedNPEMessage();
                    extendedMessageState = 2;
                }
                return extendedMessage;
            }
        }
        return message;
    }

    private native String getExtendedNPEMessage();
}
