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
    public static void main(String[] args) {
        MessageParserSAX messageParser = new MessageParserSAX();
        File xmlFile = new File("C:\\Temp\\6c2ba106-d707-4b27-8185-9cbaf071d3fb.xml");
        System.out.println(messageParser.parseMessage(xmlFile));
    }
    @Test
    public static void main1(String[] args) {
        MessageParser messageParser = new MessageParser();
        try {
            FileInputStream fis = new FileInputStream(new File("C:\\Temp\\Ошибки парсинга\\ПФР_УППО_20190920_22b673c6-dba5-11e9-9571-00155d0a1e12.XML"));
            System.out.println(messageParser.parseFile(fis));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}