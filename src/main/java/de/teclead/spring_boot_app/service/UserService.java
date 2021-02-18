package de.teclead.spring_boot_app.service;

import de.teclead.spring_boot_app.data_access_object.UserDataAccessObject;
import de.teclead.spring_boot_app.data_transfer_object.AddUserDataTransferObject;
import de.teclead.spring_boot_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDataAccessObject userDataAccessObject;

    @Autowired
    public UserService(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    public List<User> getUserList() {
        return userDataAccessObject.findAll();
    }

    public User addUser(AddUserDataTransferObject addUserDataTransferObject) {
        User userToBeSaved = User.builder()
                .firstName(addUserDataTransferObject.getFirstName())
                .lastName(addUserDataTransferObject.getLastName())
                .email(addUserDataTransferObject.getEmail())
                .build();
        userDataAccessObject.save(userToBeSaved);
        return userToBeSaved;
    }


}
