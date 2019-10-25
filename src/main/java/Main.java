import ru.hemulen.files.MyFileVisitor;
import ru.hemulen.files.ResultWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("config.ini")));
            //props.load(new FileInputStream(new File("C:\\Users\\Я\\Documents\\JavaPrj\\NpfReport\\src\\main\\resources\\config.ini")));

        } catch (IOException e) {
            System.out.println("Не удалось открыть конфигурационный файл!");
        }
        new ResultWriter(props.getProperty("REPORT_FILE"));
        ResultWriter.writeHeader();
        MyFileVisitor myFileVisitor = new MyFileVisitor();
        Path directoryPath = Paths.get(props.getProperty("DOC_DIR"));

        try {
            Files.walkFileTree(directoryPath, myFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Обработка всех вложений завершена, отчет сформирован.");
    }


}
