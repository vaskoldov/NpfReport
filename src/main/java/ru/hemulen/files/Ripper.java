package ru.hemulen.files;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * Класс Ripper предназначен для работы с архивами GZ
 */
public class Ripper {
    private static final int BUFFER_SIZE = 1024;

    /**
     * Пустой конструктор
     */
    public Ripper() {

    }

    /**
     * Метод UnpackToBuffer распаковывает архив GZ и возвращает что-то, созданное из входящего в него файла
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

    public File UnpackToTempFile(File archive) {
        byte[] buffer = new byte[BUFFER_SIZE];
        FileOutputStream fos = null;
        File outFile = null;
        try (GZIPInputStream gzInputStream = new GZIPInputStream(new FileInputStream(archive))){
            outFile = File.createTempFile("tmp-file", ".xml");
            fos = new FileOutputStream(outFile);
            int length = 0;
            while ((length = gzInputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return outFile;
    }
}
