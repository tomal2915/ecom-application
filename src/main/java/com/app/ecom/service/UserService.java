package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.repositories.UserRepository;
import com.app.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final List<User> userList = new ArrayList<>();
//    private Long nextID = 1L;

    public List<UserResponse> fetchAllUsers() {
//        return userRepository.findAll();

        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
//        users.setId(nextID++);
//        userList.add(users);

        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(Long id) {
//        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().get();
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
//        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().map(existingUser -> {
//            existingUser.setFirstName(updatedUser.getFirstName());
//            existingUser.setLastName(updatedUser.getLastName());
//            return true;
//        }).orElse(false);

        return userRepository.findById(id).map(existingUser -> {
            if (existingUser.getId().equals(id)) {
//                existingUser.setFirstName(updatedUser.getFirstName());
//                existingUser.setLastName(updatedUser.getLastName());

                updateUserFromRequest(existingUser, updatedUserRequest);
                userRepository.save(existingUser);
                return true;
            }
            return null;
        }).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(String.valueOf(userRequest.getPhoneNumber()));

        if(userRequest.getAddress() != null) {
            Address address = new Address();

            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZip(userRequest.getAddress().getZip());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        userResponse.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZip(user.getAddress().getZip());

            userResponse.setAddress(addressDTO);
        }
        return userResponse;
    }
}
