package com.example.mobileapp.dto;

import java.util.Map;

public class ReponseDTO<T> {

    public enum TypeResult {
        SUCCESS, ERROR
    }

    private TypeResult status;
    private Map<String, Object> messages;
    private T data;
    private Map<String, Object> metadata;

    public TypeResult getStatus() {
        return status;
    }

    public void setStatus(TypeResult status) {
        this.status = status;
    }

    public Map<String, Object> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Object> messages) {
        this.messages = messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

}
