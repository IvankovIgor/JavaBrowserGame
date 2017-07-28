package util;

import java.lang.reflect.Field;
import java.util.Properties;

public class ReflectionHelper {
    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldsValue(Object object, Properties properties) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            setFieldValue(object, fieldName, properties.getProperty(fieldName));
        }
    }

    public static void setFieldValue(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                field.set(object, value);
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.parseInt(value));
            }
            field.setAccessible(false);
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
