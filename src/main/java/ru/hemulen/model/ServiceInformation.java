package ru.hemulen.model;

/**
 * Класс для хранения служебной информации, извлеченной из сообщения
 */
public class ServiceInformation {
    // Атрибуты файла
    public String fileName;
    public String filePath;

    // Атрибуты сообщения
    public String GUID;
    public String replyTo;
    public String timestamp;

    public String toString() {
        String result = replyTo + ";";
        result += filePath + ";";
        result += fileName + ";";
        result += GUID + ";";
        result += timestamp + ";";
        result += "\n";
        return result;
    }

    public void clear() {
        fileName = "";
        filePath = "";
        GUID = "";
        replyTo = "";
        timestamp = "";
    }
}
