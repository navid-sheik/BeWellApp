package com.example.bewell.models;

public class Message {
    private String messageId;
    private String fromId;
    private String toId;
    private long timeStamp;
    private String textMessage;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Message(String messageId, String fromId, String toId, long timeStamp, String textMessage) {
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.timeStamp = timeStamp;
        this.textMessage = textMessage;
    }
}
