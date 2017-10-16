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
import java.util.Map;

/**
 * @author Igor Ivankov
 */
public class PageGenerator {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final String HTML_DIR = "templates";
    private static final Configuration CFG = new Configuration(new Version("2.3.26"));

    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + filename);
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