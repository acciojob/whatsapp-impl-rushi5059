package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    // Assume that each user belongs to at most one group
    // You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository() {
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public List<User> getUsersByGroup(Group group) {
        if (groupUserMap.containsKey(group)) {
            return groupUserMap.get(group);
        } else {
            return null;
        }
    }

    public List<Message> getMessagesByGroup(Group group) {
        if (groupMessageMap.containsKey(group)) {
            return groupMessageMap.get(group);
        } else {
            return null;
        }
    }

    public User getSenderByMessage(Message message) {
        if (senderMap.containsKey(message)) {
            return senderMap.get(message);
        } else {
            return null;
        }
    }

    public User getAdminByGroup(Group group) {
        if (adminMap.containsKey(group)) {
            return adminMap.get(group);
        } else {
            return null;
        }
    }

    public void addUser(User user) throws Exception {
        if (userMobile.contains(user.getMobile())) {
            throw new Exception("Mobile number already exists");
        } else {
            userMobile.add(user.getMobile());
        }
    }

    public void addGroup(Group group, List<User> users) {
        groupUserMap.put(group, users);
        groupMessageMap.put(group, new ArrayList<Message>());
        adminMap.put(group, users.get(0));
        if (users.size() == 2) {
            group.setName(users.get(1).getName());
        } else {
            customGroupCount++;
            group.setName("Group " + customGroupCount);
        }
    }

    public void addMessage(Message message, User sender, Group group) throws Exception {
        if (!groupMessageMap.containsKey(group)) {
            throw new Exception("Group does not exist");
        }
        if (!groupUserMap.get(group).contains(sender)) {
            throw new Exception("Sender not a member of the group");
        }
        messageId++;
        message.setMessageId(messageId);
        senderMap.put(message, sender);
        List<Message> messages = groupMessageMap.get(group);
        messages.add(message);
        groupMessageMap.put(group, messages);
    }

    public void changeAdmin(Group group, User approver, User user) throws Exception {
        if (!groupUserMap.containsKey(group)) {
            throw new Exception("Group does not exist");
        }
        if (!adminMap.get(group).equals(approver)) {
            throw new Exception("Approver is not the current admin");
        }
        if (!groupUserMap.get(group).contains(user)) {
            throw new Exception("User is not a part of the group");
        }
        adminMap.put(group, user);
    }

    public int removeUser(User user) throws Exception {
        boolean found = false;
        int numGroupsRemoved = 0;
        int numMessagesRemoved = 0;

        // Check if the user exists in any group
        for (Group group : groupUserMap.keySet()) {
            List<User> users = groupUserMap.get(group);
            if (users.contains(user)) {
                // Remove the user from the group
                users.remove(user);
                found = true;
                numGroupsRemoved++;
                // Remove all messages sent by the user in the group
                List<Message> messages = groupMessageMap.get(group);
                for (Message message : messages) {
                    if (senderMap.get(message).equals(user)) {
                        messages.remove(message);
                        numMessagesRemoved++;
                    }
                }
            }
        }

        if (!found) {
            throw new Exception("User does not exist");
        }

        // Remove the user from the userMobile HashSet
        userMobile.remove(user.getMobile());

        return numGroupsRemoved + numMessagesRemoved;
    }
}