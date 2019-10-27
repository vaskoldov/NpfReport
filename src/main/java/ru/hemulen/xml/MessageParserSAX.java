package ru.hemulen.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.hemulen.model.ServiceInformation;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class MessageParserSAX {
    SAXParserFactory factory;
    javax.xml.parsers.SAXParser parser;


    public MessageParserSAX() {
        factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public ServiceInformation parseMessage(File xmlFile, String orgnFileName) {
        XMLHandler handler = new XMLHandler();
        try {
            parser.parse(xmlFile, handler);
        } catch (SAXException | IOException e) {
            //e.printStackTrace();
            System.out.println(orgnFileName + " содержит 'битый' XML.");
        }
        return XMLHandler.si;
    }

    private static class XMLHandler extends DefaultHandler {
        Boolean isGUID = false;
        Boolean isDateTime = false;
        Boolean isReplyTo = false;
        static ServiceInformation si = new ServiceInformation();

        @Override
        public void startDocument() throws SAXException {
            // В начале документа чистим si, поскольку он статический
            si.clear();
        }

        @Override
        public void endDocument() throws SAXException {
            // Тут будет логика реакции на конец документа
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (localName) {
                case "GUID":
                    isGUID = true;
                    break;
                case "ВОтветНа":
                    isReplyTo = true;
                    break;
                case "ДатаВремя":
                    isDateTime = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            isGUID = false;
            isReplyTo = false;
            isDateTime = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);
            information = information.replace("\n", "").trim();
            if (!information.isEmpty()) {
                if (isGUID) si.GUID = information;
                if (isDateTime) si.timestamp = information;
                if (isReplyTo) si.replyTo = information;
            }
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на пустое пространство внутри элементов (пробелы, переносы строчек и так далее).
        }
    }
}
