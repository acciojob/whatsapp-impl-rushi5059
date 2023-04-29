package com.driver;

import java.util.Date;

public class Message {
    private int id;
    private String content;
    private User sender;
    private Group group;
    private Date timestamp;

    public Message(int messageId, String messageContent, User sender, Group group) {
        this.id = messageId;
        this.content = messageContent;
        this.sender = sender;
        this.group = group;
        this.timestamp = new Date();
    }

    public int getMessageId() {
        return id;
    }

    public void setMessageId(int messageId) {
        this.id = messageId;
    }

    public String getMessageContent() {
        return content;
    }

    public void setMessageContent(String messageContent) {
        this.content = messageContent;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
