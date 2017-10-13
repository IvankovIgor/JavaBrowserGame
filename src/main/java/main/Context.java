package main;

import sun.net.www.ApplicationLaunchException;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Object> context = new HashMap<>();

    public Object get(Class<?> clazz) {
        Object obj = context.get(clazz.getName());
        if (obj == null) {
            throw new Error("No such service in context");
        }
        return obj;
    }

    public void add(Class<?> clazz, Object obj) throws ApplicationLaunchException  {
        String className = clazz.getName();
        if (context.containsKey(className)) {
            throw new ApplicationLaunchException("Such service already exists");
        }
        context.put(className, obj);
    }
}
