package ru.hemulen_it.files;

import ru.hemulen_it.model.ServiceInformation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResultWriter {
    public static FileWriter reportFileStream;

    static {
        try {
            reportFileStream = new FileWriter(new File("C:\\Temp\\report.csv"));
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
            reportFileStream.write(file.getParent().substring(file.getParent().lastIndexOf("\\") + 1) + ";");
            reportFileStream.write(file.getName() + ";");
            reportFileStream.write('\n');
            reportFileStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
