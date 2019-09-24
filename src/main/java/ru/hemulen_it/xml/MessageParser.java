package ru.hemulen_it.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
        if (serviceInfoElement.getElementsByTagName("ВОтветНа").getLength() == 0) {
            // Сообщение является запросом
            si.requestQUID = serviceInfoElement.getElementsByTagName("GUID").item(0).getTextContent();
            si.requestTimeStamp = serviceInfoElement.getElementsByTagName("ДатаВремя").item(0).getTextContent();
            si.requestCompiler = serviceInfoElement.getElementsByTagName("Наименование").item(0).getTextContent();
            si.requestSource = serviceInfoElement.getElementsByTagName("ИсточникДанных").item(0).getTextContent();
            si.requestTransferMethod = serviceInfoElement.getElementsByTagName("СпособПередачи").item(0).getTextContent();
            si.requestExtNumber = serviceInfoElement.getElementsByTagName("НомерВнешний").item(0).getTextContent();
            si.requestFillDate = serviceInfoElement.getElementsByTagName("ДатаЗаполнения").item(0).getTextContent();
            si.requestReportDate = serviceInfoElement.getElementsByTagName("ДатаПодачи").item(0).getTextContent();
        } else {
            // Сообщение является ответом
            si.responseGUID = serviceInfoElement.getElementsByTagName("GUID").item(0).getTextContent();
            si.responseReplyTo = serviceInfoElement.getElementsByTagName("ВОтветНа").item(0).getTextContent();
            si.requestQUID = si.responseReplyTo;
            si.responseTimeStamp = serviceInfoElement.getElementsByTagName("ДатаВремя").item(0).getTextContent();
            si.responseCompiler = serviceInfoElement.getElementsByTagName("Наименование").item(0).getTextContent();
            si.responseDocumentNumber = serviceInfoElement.getElementsByTagName("НомерДокументаОрганизации").item(0).getTextContent();
            si.responsePeriod = serviceInfoElement.getElementsByTagName("ЗаГод").item(0).getTextContent();
        }
        return si;
    }
}
