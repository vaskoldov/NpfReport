package ru.hemulen_it.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ru.hemulen_it.model.ServiceInformation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

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
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc;
        try (InputStream inputStream = new ByteArrayInputStream(xml.toByteArray())) {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(inputStream);
            return extractServiceInformation(doc);
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.getMessage();
            System.out.println("Кривой документ. Обработка продолжается.");
            return null;
        }
    }
    /**
     * Метод извлекает служебную информацию из переданного в параметре XML
     * @param xml Файловый поток с XML
     * @return Структура ServiceInformation со служебной информацией документа
     *
    public ServiceInformation parseFile(FileOutputStream xml) {
        try {
            FileDescriptor xmlFD = xml.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
*/
    private ServiceInformation extractServiceInformation(Document doc) {
        ServiceInformation si = new ServiceInformation();
        Element root = doc.getDocumentElement();
        String serviceNS = getServiceNamespace(root, "пф.рф/АФ");
        si.GUID = getTextContent(root, serviceNS, "GUID");
        si.timestamp = getTextContent(root, serviceNS, "ДатаВремя");
        si.replyTo = getTextContent(root, serviceNS,"ВОтветНа");
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
        String rootNamespaceURI = element.getNamespaceURI();
        NamedNodeMap atts = element.getAttributes();
        for (int i = 0; i < atts.getLength(); i++) {
            Node node = atts.item(i);
            String name = node.getNodeName();
            String namespaceURI = node.getNodeValue();
            if (namespaceURI.contains(needle)
                    && (name != null && (XMLNAMESPACE.equals(name) || name.startsWith(XMLNAMESPACE + ":")))) {
                // Проверяем, нет ли в элементе СлужебнаяИнформация отдельного namespace пф.рф/АФ
                Node serviceInfo = element.getElementsByTagNameNS(rootNamespaceURI, "СлужебнаяИнформация").item(0);
                if ((serviceInfo != null) && (serviceInfo.getNodeType() == Node.ELEMENT_NODE)) {
                    Element serviceInfoElement = (Element) serviceInfo;
                    String serviceNamespaceURI = getServiceNamespace(serviceInfoElement, needle);
                    if (serviceNamespaceURI != null) {
                        return serviceNamespaceURI;
                    } else {
                        // Проверяем, нет ли в дочерних элементах СлужебнаяИнформация отдельного namespace пф.рф/АФ
                        NodeList serviceChilds = serviceInfoElement.getChildNodes();
                        for (int c = 0; c < serviceChilds.getLength(); c++) {
                            if ((serviceChilds.item(c) != null) && (serviceChilds.item(c).getNodeType() == Node.ELEMENT_NODE)) {
                                Element childElement = (Element) serviceChilds.item(c);
                                NamedNodeMap childAtts = childElement.getAttributes();
                                for (int j = 0; j < childAtts.getLength(); j++) {
                                    Node childNode = childAtts.item(j);
                                    String childName = childNode.getNodeName();
                                    String childNamespaceURI = childNode.getNodeValue();
                                    if ((childNamespaceURI.contains(needle)) &&
                                            (childName != null && (XMLNAMESPACE.equals(childName) || childName.startsWith(XMLNAMESPACE + ":")))
                                    ) {
                                        return childNamespaceURI;
                                    }
                                }
                            }
                        }
                    }
                }
                return namespaceURI;
            }
        }
        return null;
    }

}
