package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final List<User> userList = new ArrayList<>();
//    private Long nextID = 1L;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User users) {
//        users.setId(nextID++);
//        userList.add(users);
        userRepository.save(users);
    }

    public User fetchUser(Long id) {
//        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().get();
        return userRepository.findById(id).orElse(null);
    }

    public boolean updateUser(Long id, User updatedUser) {
//        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().map(existingUser -> {
//            existingUser.setFirstName(updatedUser.getFirstName());
//            existingUser.setLastName(updatedUser.getLastName());
//            return true;
//        }).orElse(false);

        return userRepository.findById(id).map(existingUser -> {
            if (existingUser.getId().equals(id)) {
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                userRepository.save(existingUser);
                return true;
            }
            return null;
        }).orElse(false);
    }
}
