package util.parser.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class XmlParser {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Object parse(String xmlFile) {
        logger.debug("parse:");
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            XmlHandler handler = new XmlHandler();
            saxParser.parse(xmlFile, handler);

            return handler.getObject();

        } catch (IOException | ParserConfigurationException | SAXException | RuntimeException e) {
            throw new SettingsFileNotFoundException(e);
        }
    }
}
