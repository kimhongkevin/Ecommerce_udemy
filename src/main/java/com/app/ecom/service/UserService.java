package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/* List base -- operation

@Service
@RequiredArgsConstructor
public class UserService {
    private List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> fetchAllUsers() {
        return userList;
    }

    public Optional<User> fetchUser(Long id) {
//        for (User user : userList) {
//            if( user.getId().equals(id)) {
//                return user;
//            }
//        }
        return userList.stream().filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public void addUser(User user) {
        user.setId(nextId++);
        userList.add(user);

    }

    public boolean updateUser(Long id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map( existingUser ->{
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}
*/
// With database
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepo;

    public List<UserResponse> fetchAllUsers(){

        return userRepo.findAll().stream()
                .map(this::mapToUserResponse).collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id){

        return userRepo.findById(id).map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateFromUserRequest(userRequest,user);
        userRepo.save(user);
    }

    public boolean updateUser(Long id,UserRequest updatedUserFromRequest){
        return userRepo.findById(id)
                .map(existingUser -> {
                    updateFromUserRequest(updatedUserFromRequest,existingUser);
                    userRepo.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());

        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());

            userResponse.setAddress(addressDTO);

        }
        return userResponse;
    }

    private void updateFromUserRequest(UserRequest userRequest, User user){
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }
}
