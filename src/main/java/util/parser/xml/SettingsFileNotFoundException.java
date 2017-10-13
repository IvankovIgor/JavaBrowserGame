package util.parser.xml;

public class SettingsFileNotFoundException extends RuntimeException {
    public SettingsFileNotFoundException(Exception e) {
        super(e);
    }
}
