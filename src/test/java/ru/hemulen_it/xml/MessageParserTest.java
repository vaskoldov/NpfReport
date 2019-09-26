package ru.hemulen_it.xml;

import org.junit.Test;
import ru.hemulen_it.model.ServiceInformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageParserTest {
    @Test
    public static void main(String[] args) {
        MessageParser messageParser = new MessageParser();
        File xmlFile = new File("C:\\Temp\\ПФР_027007_7726260499_ДНПФД_20190919_c1b4ccae-dae8-11e9-8bda-005056897fe0.xml");
        byte[] xml = null;
        try {
            xml = Files.readAllBytes(Paths.get("C:\\Temp\\ПФР_027007_7726260499_ДНПФД_20190919_c1b4ccae-dae8-11e9-8bda-005056897fe0.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream xmlStream = new ByteArrayOutputStream(xml.length);
        xmlStream.write(xml, 0, xml.length);
        System.out.println(messageParser.parseMessage(xmlStream));
    }
}