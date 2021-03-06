package de.teclead.spring_boot_app.service;

import de.teclead.spring_boot_app.data_access_object.UserDataAccessObject;
import de.teclead.spring_boot_app.data_transfer_object.AddUserDataTransferObject;
import de.teclead.spring_boot_app.data_transfer_object.UpdateUserDataTransferObject;
import de.teclead.spring_boot_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    public User updateUser(UpdateUserDataTransferObject updateUserDataTransferObject) {
        userDataAccessObject
                .findById(updateUserDataTransferObject.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        User userToBeUpdated = User.builder()
                .id(updateUserDataTransferObject.getId())
                .firstName(updateUserDataTransferObject.getFirstName())
                .lastName(updateUserDataTransferObject.getLastName())
                .email(updateUserDataTransferObject.getEmail())
                .build();

        userDataAccessObject.save(userToBeUpdated);
        return userToBeUpdated;
    }

    public void deleteUser(String userId) {
        userDataAccessObject
                .findById(Integer.parseInt(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userDataAccessObject
                .deleteById(Integer.parseInt(userId));
    }

    public List<User> getUserListWithSameFistName(String firstName) {
        return userDataAccessObject.findAllByFirstNameEquals(firstName);
    }
}
