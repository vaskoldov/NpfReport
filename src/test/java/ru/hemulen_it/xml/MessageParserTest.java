package ru.hemulen_it.xml;

import org.junit.Test;
import ru.hemulen_it.model.ServiceInformation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageParserTest {
    @Test
    public static void main2(String[] args) {
        MessageParser messageParser = new MessageParser();
        byte[] xml = null;
        try {
            xml = Files.readAllBytes(Paths.get("C:\\Temp\\Ошибки парсинга\\ПФР_УППО_20190920_22b673c6-dba5-11e9-9571-00155d0a1e12.XML"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream xmlStream = new ByteArrayOutputStream(xml.length);
        xmlStream.write(xml, 0, xml.length);
        System.out.println(messageParser.parseMessage(xmlStream));
    }
    @Test
    public static void main(String[] args) {
        MessageParser messageParser = new MessageParser();
        try {
            FileInputStream fis = new FileInputStream(new File("C:\\Temp\\Ошибки парсинга\\ПФР_УППО_20190920_22b673c6-dba5-11e9-9571-00155d0a1e12.XML"));
            System.out.println(messageParser.parseFile(fis));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}