package util;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Map;

/**
 * @author Igor Ivankov
 */
public class PageGenerator {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final String TEMPLATE_DIR = "templates";
    private static final Configuration CFG = new Configuration(new Version("2.3.26"));
    static {
        try {
            URL url = PageGenerator.class.getClassLoader().getResource(TEMPLATE_DIR);
            if (url != null) {
                CFG.setDirectoryForTemplateLoading(new File(url.getPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(filename);
            template.process(data, stream);
        } catch (TemplateException | MalformedTemplateNameException | ParseException | TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.warn("IOException for file: " + filename);
            e.printStackTrace();
        }
        return stream.toString();
    }
}