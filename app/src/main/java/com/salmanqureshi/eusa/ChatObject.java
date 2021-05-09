package com.salmanqureshi.eusa;

public class ChatObject {
    private String sender;
    private String messageId;
    private String message;

    public ChatObject(String sender, String messageId, String message) {
        this.sender = sender;
        this.messageId = messageId;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
