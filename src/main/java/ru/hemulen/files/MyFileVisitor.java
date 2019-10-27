package ru.hemulen.files;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import ru.hemulen.model.ServiceInformation;
import ru.hemulen.xml.MessageParserSAX;

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
        // Если нулевой архив, то пишем его название и не парсим
        if (arcFile.length() == 0L) {
            System.out.println(arcFile.getName() + " - нулевой длины.");
            return;
        }
        File xmlFile = null;
        try {
            xmlFile = ripper.UnpackToTempFile(arcFile);
        } catch (IOException e) {
            System.out.println(arcFile.getName() + " не удалось распаковать.");
            return;
        }
        // ...парсим, выбирая элементы со служебной информацией
        MessageParserSAX parser = new MessageParserSAX();
        ServiceInformation si = parser.parseMessage(xmlFile, arcFile.getName());
        if (si != null) {
            si.fileName = arcFile.getName();
            si.filePath = arcFile.getParent().substring(arcFile.getParent().lastIndexOf(File.separator) + 1);
            // ...записываем в глобальный список
            ResultWriter.writeResult(si);
            si.clear();
        } else {
            ResultWriter.writeError(arcFile);
        }
    }
}
