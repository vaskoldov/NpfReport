package ru.hemulen_it.files;

import ru.hemulen_it.model.ServiceInformation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResultWriter {
    public static String fileName;
    public static FileWriter reportFileStream;

    public ResultWriter(String argFileName) {
        fileName = argFileName;
        try {
            reportFileStream = new FileWriter(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeHeader() {
        try {
            // Записываем BOM в начало файла
            //reportFileStream.write(new Character('\uFEFF'));
            // Записываем заголовок
            reportFileStream.write("ВОтветНа;");
            reportFileStream.write("Каталог;");
            reportFileStream.write("Файл;");
            reportFileStream.write("GUID;");
            reportFileStream.write("ДатаВремя");
            reportFileStream.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeResult(ServiceInformation si) {
        try {
            reportFileStream.write(si.toString());
            reportFileStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeError(File file) {
        try {
            reportFileStream.write("parsing error;");
            reportFileStream.write(file.getParent().substring(file.getParent().lastIndexOf(File.pathSeparatorChar) + 1) + ";");
            reportFileStream.write(file.getName() + ";");
            reportFileStream.write('\n');
            reportFileStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setFileName(String argFileName) {
        fileName = argFileName;
    }
}
