package com.driver;

import java.util.Date;
import java.util.List;

public class WhatsappService {
    public String createUser(String name, String mobile) {
        return WhatsappRepository.saveUser(name, mobile);
    }

    public Group createGroup(List<User> users) {
        return null;
    }

    public int createMessage(String content) {
        return 0;
    }

    public int sendMessage(Message message, User sender, Group group) {
        return 0;
    }

    public String changeAdmin(User approver, User user, Group group) {
        return null;
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}
