package main;

import entity.resource.Resource;
import util.parser.properties.PropertiesParser;
import util.parser.xml.XmlParser;
import util.vfs.Vfs;
import util.vfs.VfsImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ResourceSingleton {
    private static ResourceSingleton resourceSingleton;
    Map<String, Resource> resources = new HashMap<>();

    private ResourceSingleton() {}

    public static synchronized ResourceSingleton getInstance() {
        if (resourceSingleton == null) {
            resourceSingleton = new ResourceSingleton();
        }
        return resourceSingleton;
    }

    @Deprecated
    public void loadAllResources(String resourceDirectory) {
        Vfs vfs = new VfsImpl(resourceDirectory);
        Iterator<String> iterator = vfs.getIterator();

        while (iterator.hasNext()) {
            String resourcePath = iterator.next();
            loadResource(resourcePath);
        }
    }

    public boolean loadResource(String resourcePath) {
        if (isValidPath(resourcePath)) {
            Resource resource;
            String fileExtension = getFileExtension(resourcePath);
            switch (fileExtension) {
                case "properties":
                    PropertiesParser propertiesParser = new PropertiesParser();
                    propertiesParser.parse(resourcePath);
                    resource = (Resource) propertiesParser.getObject();
                    break;
                case "xml":
                    resource = (Resource) XmlParser.readXML(resourcePath);
                    break;
                default:
                    return false;
            }
            resources.put(resource.getClass().getName(), resource);
            return true;
        } else {
            return false;
        }
    }

    private String getFileExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    private boolean isValidPath(String path) {
        return path.lastIndexOf('.') != -1 && path.lastIndexOf('.') != 0;
    }

    public Resource getResource(Class<?> resourceClass) {
        return resources.get(resourceClass.getName());
    }

}
