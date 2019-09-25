package ru.hemulen_it;

import ru.hemulen_it.files.MyFileVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        /*
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("config.ini")));
        } catch (IOException e) {
            System.out.println("Не удалось открыть конфигурационный файл!");
        }

         */
        MyFileVisitor myFileVisitor = new MyFileVisitor();
        //Path directoryPath = Paths.get(props.getProperty("DOC_DIR"));
        Path directoryPath = Paths.get("D:\\\\Temp\\\\base-storage\\\\770I01");
        try {
            Files.walkFileTree(directoryPath, myFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
