package cn.xgl.mbg.config.xml;

import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser;
import org.mybatis.generator.config.xml.ParserEntityResolver;
import org.mybatis.generator.config.xml.ParserErrorHandler;
import org.mybatis.generator.exception.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @Description:
 * @Author: xgl
 * @Date: 2022/10/26 10:41
 */
public class ConfigurationParserOverride extends ConfigurationParser {

    private final List<String> warnings;
    private final List<String> parseErrors;
    private final Properties extraProperties;

    public ConfigurationParserOverride(List<String> warnings) {
        this(null, warnings);
    }

    /**
     * This constructor accepts a properties object which may be used to specify
     * an additional property set.  Typically this property set will be Ant or Maven properties
     * specified in the build.xml file or the POM.
     *
     * <p>If there are name collisions between the different property sets, they will be
     * resolved in this order:
     *
     * <ol>
     *   <li>System properties take highest precedence</li>
     *   <li>Properties specified in the &lt;properties&gt; configuration
     *       element are next</li>
     *   <li>Properties specified in this "extra" property set are
     *       lowest precedence.</li>
     * </ol>
     *
     * @param extraProperties an (optional) set of properties used to resolve property
     *     references in the configuration file
     * @param warnings any warnings are added to this array
     */
    public ConfigurationParserOverride(Properties extraProperties, List<String> warnings) {
        super(extraProperties, warnings);
        this.extraProperties = extraProperties;

        if (warnings == null) {
            this.warnings = new ArrayList<>();
        } else {
            this.warnings = warnings;
        }

        parseErrors = new ArrayList<>();
    }

    @Override
    public Configuration parseConfiguration(File inputFile) throws IOException,
            XMLParserException {

        FileReader fr = new FileReader(inputFile);

        return parseConfiguration(fr);
    }

    @Override
    public Configuration parseConfiguration(Reader reader) throws IOException,
            XMLParserException {

        InputSource is = new InputSource(reader);

        return parseConfiguration(is);
    }

    @Override
    public Configuration parseConfiguration(InputStream inputStream)
            throws IOException, XMLParserException {

        InputSource is = new InputSource(inputStream);

        return parseConfiguration(is);
    }

    private Configuration parseConfiguration(InputSource inputSource)
            throws IOException, XMLParserException {
        parseErrors.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        factory.setValidating(true);

        try {
            //factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ParserEntityResolver());

            ParserErrorHandler handler = new ParserErrorHandler(warnings,
                    parseErrors);
            builder.setErrorHandler(handler);

            Document document = null;
            try {
                document = builder.parse(inputSource);
            } catch (SAXParseException e) {
                throw new XMLParserException(parseErrors);
            } catch (SAXException e) {
                if (e.getException() == null) {
                    parseErrors.add(e.getMessage());
                } else {
                    parseErrors.add(e.getException().getMessage());
                }
            }

            if (document == null || !parseErrors.isEmpty()) {
                throw new XMLParserException(parseErrors);
            }

            Configuration config;
            Element rootNode = document.getDocumentElement();
            DocumentType docType = document.getDoctype();
            config = parseMyBatisGeneratorConfiguration(rootNode);

            if (!parseErrors.isEmpty()) {
                throw new XMLParserException(parseErrors);
            }

            return config;
        } catch (ParserConfigurationException e) {
            parseErrors.add(e.getMessage());
            throw new XMLParserException(parseErrors);
        }
    }

    private Configuration parseMyBatisGeneratorConfiguration(Element rootNode)
            throws XMLParserException {
        MyBatisGeneratorConfigurationParser parser = new MyBatisGeneratorConfigurationParserOverride(
                extraProperties);
        return parser.parseConfiguration(rootNode);
    }
}
