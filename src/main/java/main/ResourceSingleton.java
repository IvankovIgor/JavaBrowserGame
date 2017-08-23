package main;

import entity.resource.Resource;
import util.properties.PropertiesFileParser;
import util.sax.SaxXmlReader;
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

    public void loadAllResources(String resourceDirectory) {
        Vfs vfs = new VfsImpl(resourceDirectory);
        Iterator<String> iterator = vfs.getIterator(resourceDirectory);

        while (iterator.hasNext()) {
            String resourcePath = iterator.next();
            loadResource(resourcePath);
        }
    }

    private Resource loadResource(String resourcePath) {
        if (isValidPath(resourcePath) && !resources.containsKey(resourcePath)) {
            Resource resource;
            String fileExtension = getFileExtension(resourcePath);
            switch (fileExtension) {
                case "properties":
                    PropertiesFileParser propertiesFileParser = new PropertiesFileParser();
                    propertiesFileParser.parse(resourcePath);
                    resource = (Resource) propertiesFileParser.getObject();
                    break;
                case "xml":
                    resource = (Resource) SaxXmlReader.readXML(resourcePath);
                    break;
                default:
                    return null;
            }
            resources.put(resource.getClass().getSimpleName(), resource);
        }
        return resources.get(resourcePath);
    }

    private String getFileExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    private boolean isValidPath(String path) {
        return path.lastIndexOf('.') != -1 && path.lastIndexOf('.') != 0;
    }

    public Resource getResource(String resourceName) {
        return resources.get(resourceName);
    }

}
