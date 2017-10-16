package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public class Context {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private Map<String, Object> context = new HashMap<>();

    public Object get(Class<?> clazz) {
        Object obj = context.get(clazz.getName());
        if (obj == null) {
            throw new Error("No such service in context");
        }
        return obj;
    }

    public void add(Class<?> clazz, @Nullable Object obj) {
        String className = clazz.getName();
        if (obj == null || context.containsKey(className)) {
            logger.warn(clazz.getName() + " wasn't added");
        }
        context.put(className, obj);
    }
}
