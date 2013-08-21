package cz.nkp.differ.compare.metadata.external;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author xrosecky
 */
public class XSLTTransformer implements ResultTransformer {

    private String stylesheet;
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    @Override
    public List<Entry> transform(byte[] stdout, byte[] stderr) throws IOException {
        Resource resource = resourceLoader.getResource(stylesheet);
        if (stylesheet == null) {
            throw new NullPointerException("stylesheet");
        }
        List<Entry> entries = new ArrayList<Entry>();
        try {
           // Debug transformer input
           // System.err.println(new String(stdout));

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(resource.getInputStream());
            Transformer transformer = factory.newTransformer(xslt);
            Source src = new StreamSource(new ByteArrayInputStream(stdout));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            transformer.transform(src, new StreamResult(os));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(os.toByteArray()));
            NodeList nodeList = doc.getElementsByTagName("property");
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String key = node.getAttributes().getNamedItem("name").getNodeValue();
                    String source = null;
                    if (node.getAttributes().getNamedItem("source") != null) {
                        source = node.getAttributes().getNamedItem("source").getNodeValue();
                    }
                    String value = node.getTextContent();
                    Entry entry = new Entry();
                    entry.setKey(key);
                    entry.setValue(value);
                    entry.setSource(source);
                    entries.add(entry);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entries;
    }
}
