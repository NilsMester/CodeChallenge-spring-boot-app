package de.teclead.spring_boot_app.controller;

import de.teclead.spring_boot_app.data_transfer_object.AddUserDataTransferObject;
import de.teclead.spring_boot_app.data_transfer_object.UpdateUserDataTransferObject;
import de.teclead.spring_boot_app.model.User;
import de.teclead.spring_boot_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUserList(){
        return this.userService.getUserList();
    }

    @PostMapping
    public User addUser(@RequestBody AddUserDataTransferObject addUserDataTransferObject){
        return this.userService.addUser(addUserDataTransferObject);
    }

    @PutMapping("{userId}")
    public User updateUser(@RequestBody UpdateUserDataTransferObject updateUserDataTransferObject,  @PathVariable String userId){
        if(!userId.equals(updateUserDataTransferObject.getId().toString())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return this.userService.updateUser(updateUserDataTransferObject);
    }



}
