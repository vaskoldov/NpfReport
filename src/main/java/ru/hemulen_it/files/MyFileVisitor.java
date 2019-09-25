package ru.hemulen_it.files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import ru.hemulen_it.model.ServiceInformation;
import ru.hemulen_it.xml.MessageParser;

public class MyFileVisitor extends SimpleFileVisitor {
    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".gz")) {
            // Если текущий файл - архив, то распаковываем его...
            Ripper ripper = new Ripper();
            File arcFile = new File(file.toString());
            ByteArrayOutputStream xml = ripper.UnpackToBuffer(arcFile);
            // ...парсим, выбирая элементы со служебной информацией
            MessageParser parser = new MessageParser();
            ServiceInformation si = parser.parseMessage(xml);
            if (si != null){
                si.fileName = arcFile.getName();
                si.filePath = arcFile.getParent();

                System.out.println(si.fileName);
                System.out.println(si.filePath);
                System.out.println(si.GUID);
                System.out.println(si.replyTo);
                System.out.println(si.timeStamp);
                System.out.println(si.compiler);
                System.out.println(si.source);
                System.out.println(si.transferMethod);
                System.out.println(si.extNumber);
                System.out.println(si.fillDate);
                System.out.println(si.reportDate);
                System.out.println(si.documentNumber);
                System.out.println(si.period);
            }
            // ...записываем в глобальный список

        }
        return FileVisitResult.CONTINUE;
    }
}
