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
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(".gz")) {
            visitFileController(file);
        }
        return FileVisitResult.CONTINUE;
    }

    protected void visitFileController(Object file) {
        Ripper ripper = new Ripper();
        File arcFile = new File(file.toString());
        ByteArrayOutputStream xml = ripper.UnpackToBuffer(arcFile);
        // ...парсим, выбирая элементы со служебной информацией
        MessageParser parser = new MessageParser();
        ServiceInformation si = parser.parseMessage(xml);
        try {
            xml.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (si != null) {
            si.fileName = arcFile.getName();
            si.filePath = arcFile.getParent().substring(arcFile.getParent().lastIndexOf("\\") + 1);
            // ...записываем в глобальный список
            ResultWriter.writeResult(si);
        } else {
            ResultWriter.writeError(arcFile);
        }
    }
}
