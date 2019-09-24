package ru.hemulen_it.model;

/**
 * Класс для хранения служебной информации, извлеченной из сообщения
 */
public class ServiceInformation {
    // Атрибуты запроса
    public String requestQUID;
    public String requestTimeStamp;
    public String requestCompiler;
    public String requestSource;
    public String requestTransferMethod;
    public String requestExtNumber;
    public String requestFillDate;
    public String requestReportDate;

    // Атрибуты ответа
    public String responseGUID;
    public String responseReplyTo;
    public String responseTimeStamp;
    public String responseCompiler;
    public String responseDocumentNumber;
    public String responsePeriod;
}
