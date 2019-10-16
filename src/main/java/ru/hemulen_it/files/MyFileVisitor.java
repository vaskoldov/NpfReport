package ru.hemulen_it.files;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import ru.hemulen_it.model.ServiceInformation;
import ru.hemulen_it.xml.MessageParser;
import ru.hemulen_it.xml.MessageParserSAX;

public class MyFileVisitor extends SimpleFileVisitor {
    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(".gz")) {
            visitFileController(file);
        }
        return FileVisitResult.CONTINUE;
    }

    protected void visitFileController(Object file) {
        Ripper ripper = new Ripper();
        File arcFile = new File(file.toString());
        //System.out.println(arcFile.toString());
        //System.out.println(arcFile.length());
        // Если нулевой архив, то пишем его название и не парсим
        if (arcFile.length() == 0L) {
            System.out.println(arcFile.getName() + " - нулевой длины!");
            return;
        }
        // Если файл слишком большой (больше 10 Мб), то пишем его название и не парсим
 //       if (arcFile.length() > 10000000L) {
 //           System.out.println(arcFile.getName() + " - слишком большой для автоматической обработки!");
 //           return;
 //       }
        File xmlFile = ripper.UnpackToTempFile(arcFile);
        // ...парсим, выбирая элементы со служебной информацией
        MessageParserSAX parser = new MessageParserSAX();
//        FileInputStream xmlFis = null;
        ServiceInformation si = null;
        //            xmlFis = new FileInputStream(xmlFile);
        si = parser.parseMessage(xmlFile);
        if (si != null) {
            si.fileName = arcFile.getName();
            si.filePath = arcFile.getParent().substring(arcFile.getParent().lastIndexOf(File.separator) + 1);
            // ...записываем в глобальный список
            ResultWriter.writeResult(si);
        } else {
            ResultWriter.writeError(arcFile);
        }
    }
}
