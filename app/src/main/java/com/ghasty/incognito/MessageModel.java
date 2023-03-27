package com.ghasty.incognito;

public class MessageModel {
    private String message, createdAt;

    public MessageModel(String message, String createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
