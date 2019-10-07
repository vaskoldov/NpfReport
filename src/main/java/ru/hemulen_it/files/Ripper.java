package ru.hemulen_it.files;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * Класс Ripper предназначен для работы с архивами
 */
public class Ripper {
    private static final int BUFFER_SIZE = 1024;

    /**
     * Пустой конструктор
     */
    public Ripper() {

    }
    /**
     * Метод GZDissect распаковывает архив GZ и возвращает что-то, созданное из входящего в него файла
     */
    public ByteArrayOutputStream UnpackToBuffer(File archive) {
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzInputStream = new GZIPInputStream(new FileInputStream(archive))) {
            int length = 0;
            while ((length = gzInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
