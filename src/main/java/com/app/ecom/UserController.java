package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // Added to avoid repeating path in every method
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users); // Returns 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.fetchUser(id);
        return ResponseEntity.ok(user); // Returns 200 OK
    }

    @PostMapping
    public ResponseEntity<String> createUsers(@RequestBody User user) {
        userService.addUser(user);
        // Returns 201 Created (standard for successful POST)
        return new ResponseEntity<>("User Added Successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean updated = userService.updateUser(id, updatedUser);
        if (updated) {
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Updated Failed", HttpStatus.BAD_REQUEST);
    }
}

