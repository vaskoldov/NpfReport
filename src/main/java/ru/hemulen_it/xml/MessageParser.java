package ru.hemulen_it.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ru.hemulen_it.model.ServiceInformation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MessageParser {
    // Константа для поиска атрибутов xmlns
    private final String XMLNAMESPACE = "xmlns";

    /**
     * Метод извлекает служебную информацию из переданного в параметре байтового потока
     * @param xml Строка XML, обернутая в байтовый поток вывода
     * @return Структура ServiceInformation со служебной информацией документа
     */
    public ServiceInformation parseMessage(ByteArrayOutputStream xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setNamespaceAware(true);
        DocumentBuilder builder;
        InputStream inputStream = new ByteArrayInputStream(xml.toByteArray());
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(inputStream);
            return extractServiceInformation(doc);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ServiceInformation extractServiceInformation(Document doc) {
        ServiceInformation si = new ServiceInformation();
        Element root = doc.getDocumentElement();
        String serviceNS = getServiceNamespace(root, "пф.рф/АФ");
        Element serviceInfoElement = (Element) root.getElementsByTagName("СлужебнаяИнформация").item(0);
        if (serviceInfoElement == null) {
            // В сообщении нет блока служебной информации
            return null;
        }
        si.GUID = getTextContent(root, serviceNS, "GUID");
        si.timestamp = getTextContent(serviceInfoElement, serviceNS, "ДатаВремя");
        si.replyTo = getTextContent(serviceInfoElement, serviceNS,"ВОтветНа");
        return si;
    }

    private String getTextContent(Element root, String namespace, String nodeName) {
        NodeList nodeList = root.getElementsByTagNameNS(namespace, nodeName);
        if (nodeList.getLength() != 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return "";
        }
    }

    private String getServiceNamespace(Element element, String needle) {
        NamedNodeMap atts = element.getAttributes();
        for (int i = 0; i < atts.getLength(); i++) {
            Node node = atts.item(i);
            String name = node.getNodeName();
            String namespaceURI = node.getNodeValue();
            if (namespaceURI.contains(needle)
                    && (name != null && (XMLNAMESPACE.equals(name) || name.startsWith(XMLNAMESPACE + ":")))) {
                return namespaceURI;
            }
        }
        return null;
    }

}
