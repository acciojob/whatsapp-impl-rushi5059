package com.driver;

import java.util.List;
import java.util.Map;

public class Group {
    private String name;
    private int numberOfParticipants;
    private User admin;
    private List<User> participants;
    private Map<Integer, Message> messages;
    private int numberOfMessages;

    public void setName(String name) {
        this.name = name;
    }

    public Group(String name, User admin, List<User> participants, Map<Integer, Message> messages) {
        this.name = name;
        this.admin = admin;
        this.participants = participants;
        this.messages = messages;
        this.numberOfParticipants = participants.size();
        this.numberOfMessages = messages.size();
    }

    public String getName() {
        return name;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
        this.numberOfParticipants = participants.size();
    }

    public Map<Integer, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<Integer, Message> messages) {
        this.messages = messages;
        this.numberOfMessages = messages.size();
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public void addParticipant(User user) {
        if (participants.contains(user)) {
            throw new IllegalArgumentException("User already exists in the group!");
        }
        participants.add(user);
        numberOfParticipants++;
    }

    public void removeParticipant(User user) {
        if (!participants.contains(user)) {
            throw new IllegalArgumentException("User does not exist in the group!");
        }
        if (user.equals(admin)) {
            throw new IllegalArgumentException("Cannot remove the admin from the group!");
        }
        participants.remove(user);
        numberOfParticipants--;
        for (Integer messageId : messages.keySet()) {
            Message message = messages.get(messageId);
            if (message.getSender().equals(user)) {
                messages.remove(messageId);
                numberOfMessages--;
            }
        }
    }

    public int sendMessage(Message message, User sender) {
        if (!participants.contains(sender)) {
            throw new IllegalArgumentException("Sender is not a member of the group!");
        }
        messages.put(numberOfMessages + 1, message);
        numberOfMessages++;
        return numberOfMessages;
    }

    public void changeAdmin(User approver, User user) {
        if (!participants.contains(approver)) {
            throw new IllegalArgumentException("Approver is not a member of the group!");
        }
        if (!admin.equals(approver)) {
            throw new IllegalArgumentException("Approver is not the admin of the group!");
        }
        if (!participants.contains(user)) {
            throw new IllegalArgumentException("User is not a member of the group!");
        }
        admin = user;
    }

}
