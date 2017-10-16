package main;

import entity.resource.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.parser.Parser;
import util.parser.properties.PropertiesParser;
import util.parser.xml.XmlParser;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

public final class ResourceSingleton {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static final String PATH_TO_PROPERTIES = "src/main/resources/";
    public static final String MYSQL_PROPERTIES = "mysql.properties";
    public static final String MYSQL_TEST_PROPERTIES = "mysql-test.properties";
    public static final String POSTGRES_PROPERTIES = "postgres.properties";
    public static final String POSTGRES_TEST_PROPERTIES = "postgres-test.properties";
    public static final String SERVER_PROPERTIES = "server.properties";
    public static final String GAME_PROPERTIES = "game.properties";

    private static ResourceSingleton resourceSingleton;
    private Map<String, Resource> resources = new HashMap<>();

    private ResourceSingleton() {}

    public static synchronized ResourceSingleton getInstance() {
        if (resourceSingleton == null) {
            resourceSingleton = new ResourceSingleton();
        }
        return resourceSingleton;
    }

    public Resource getResource(String resourceName) {
        if (!resources.containsKey(resourceName)) {
            if (!loadResource(resourceName)) {
                logger.warn("Resource \"" + resourceName + "\" wasn't loaded.");
            }
        }
        return resources.get(resourceName);
    }

    private boolean loadResource(String resourceName) {
        String resourcePath = PATH_TO_PROPERTIES + resourceName;
        if (isValidPath(resourcePath)) {
            Parser parser;
            String fileExtension = getFileExtension(resourcePath);
            switch (fileExtension) {
                case "properties":
                    parser = PropertiesParser::parse;
                    break;
                case "xml":
                    parser = XmlParser::parse;
                    break;
                default:
                    return false;
            }
            Resource resource = (Resource) parser.parse(resourcePath);
            resources.put(resourceName, resource);
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
}
