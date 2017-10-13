package util.parser.properties;

import util.ReflectionHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesParser {
    private Object object = null;

    public void parse(String filename) {
        try (final FileInputStream settingsFile = new FileInputStream(filename)) {
            final Properties properties = new Properties();

            properties.load(settingsFile);

            object = ReflectionHelper.createInstance(properties.getProperty("class"));
            // TODO
            ReflectionHelper.setFieldsValue(object, properties);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject() {
        return object;
    }
}
