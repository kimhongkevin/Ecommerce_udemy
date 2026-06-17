package com.app.ecom.service;

import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> fetchAllUsers(){
        return userRepo.findAll();
    }

    public Optional<User> fetchUser(Long id){
        return userRepo.findById(id);
    }

    public void addUser(User user){
        userRepo.save(user);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public boolean updateUser(Long id,User updatedUser){
        return userRepo.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepo.save(existingUser);
                    return true;
                }).orElse(false);
    }
}
