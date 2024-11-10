package store.view;

public enum Answer {
    YES("Y"),
    NO("N"),
    ;

    private final String format;

    Answer(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
