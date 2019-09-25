package ru.hemulen_it.xml;

import ru.hemulen_it.model.ServiceInformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageParserTest {
    public ServiceInformation parseMessage() {
        MessageParser messageParser = new MessageParser();
        File xmlFile = new File("D:\\Temp\\ПФР_087001_7726260499_ДНПФД_20190710_2891791a-a30f-11e9-ac9c-005056897fe0.xml");
        ByteArrayOutputStream xml = new ByteArrayOutputStream();
        return messageParser.parseMessage(xml);
    }
}