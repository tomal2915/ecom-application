package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> userList = new ArrayList<>();
    private Long nextID = 1L;

    public List<User> fetchAllUsers() {
        return userList;
    }

    public void addUser (User users) {
        users.setId(nextID++);
        userList.add(users);
    }

    public User fetchUser(Long id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }
}
