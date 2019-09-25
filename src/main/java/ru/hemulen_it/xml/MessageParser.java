package ru.hemulen_it.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
    public ServiceInformation parseMessage(ByteArrayOutputStream xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
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
        Element serviceInfoElement = (Element) root.getElementsByTagName("СлужебнаяИнформация").item(0);
        if (serviceInfoElement == null) {
            // В сообщении нет блока служебной информации
            return null;
        }
        si.GUID = getTextContent(root, "GUID");
        si.timeStamp = getTextContent(serviceInfoElement, "ДатаВремя");
        si.compiler = getTextContent(serviceInfoElement, "Наименование");
        si.source = getTextContent(serviceInfoElement, "ИсточникДанных");
        si.transferMethod = getTextContent(serviceInfoElement,"СпособПередачи");
        si.extNumber = getTextContent(serviceInfoElement,"НомерВнешний");
        si.fillDate = getTextContent(serviceInfoElement,"ДатаЗаполнения");
        si.reportDate = getTextContent(serviceInfoElement,"ДатаПодачи");
        si.replyTo = getTextContent(serviceInfoElement,"ВОтветНа");
        si.documentNumber = getTextContent(serviceInfoElement,"НомерДокументаОрганизации");
        si.period = getTextContent(serviceInfoElement,"ЗаГод");
        return si;
    }

    private String getTextContent(Element root, String nodeName) {
        NodeList nodeList = root.getElementsByTagName(nodeName);
        if (nodeList.getLength() != 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return "";
        }
    }

}
