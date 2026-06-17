package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
//        User user = userService.fetchUser(id);
//        if(user == null)
//            return ResponseEntity.notFound().build();

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<String> createUsers(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsers(@PathVariable Long id, @RequestBody User updatedUser){

        return userService.updateUser(id,updatedUser) ?
                ResponseEntity.ok("User updated successfully") :
                ResponseEntity.notFound().build();
    }

}
