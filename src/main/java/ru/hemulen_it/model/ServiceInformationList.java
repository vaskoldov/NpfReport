package ru.hemulen_it.model;

import java.util.*;

public class ServiceInformationList extends HashMap<String, ServiceInformation> {
    public ServiceInformation add(ServiceInformation serviceInformation) {
        if (serviceInformation.responseReplyTo != null) {
            // Обновляем существующую запись
            ServiceInformation request = super.get(serviceInformation.requestQUID);
            request.responseGUID = serviceInformation.responseGUID;
            request.responseReplyTo = serviceInformation.responseReplyTo;
            request.responseTimeStamp = serviceInformation.responseTimeStamp;
            request.responseCompiler = serviceInformation.responseCompiler;
            request.responseDocumentNumber = serviceInformation.responseDocumentNumber;
            return request;
        } else {
            return super.put(serviceInformation.requestQUID, serviceInformation);
        }
    }
}
