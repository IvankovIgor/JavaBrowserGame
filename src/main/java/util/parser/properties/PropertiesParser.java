package util.parser.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import util.ReflectionHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class PropertiesParser {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static @Nullable Object parse(String filename) {
        try (final FileInputStream settingsFile = new FileInputStream(filename)) {
            final Properties properties = new Properties();

            properties.load(settingsFile);

            Object object = ReflectionHelper.createInstance(properties.getProperty("class"));
            // TODO
            ReflectionHelper.setFieldsValue(object, properties);
            return object;
        } catch (FileNotFoundException e) {
            logger.warn(filename + " not found");
            e.printStackTrace();
        } catch (IOException e) {
            logger.warn("IOException during " + filename + " parsing");
            e.printStackTrace();
        }
        return null;
    }
}
