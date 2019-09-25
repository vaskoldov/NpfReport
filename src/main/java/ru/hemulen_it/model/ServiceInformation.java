package ru.hemulen_it.model;

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
        return "GUID: " + GUID + ", timestamp: " + timestamp + ", replyTo: " + replyTo;
    }
}
