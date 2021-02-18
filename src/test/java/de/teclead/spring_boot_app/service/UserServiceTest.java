package de.teclead.spring_boot_app.service;

import de.teclead.spring_boot_app.data_access_object.UserDataAccessObject;
import de.teclead.spring_boot_app.data_transfer_object.AddUserDataTransferObject;
import de.teclead.spring_boot_app.data_transfer_object.UpdateUserDataTransferObject;
import de.teclead.spring_boot_app.model.User;
import de.teclead.spring_boot_app.seeder.UserSeeder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@Profile("development")
public class UserServiceTest {

    final UserDataAccessObject userDataAccessObject = mock(UserDataAccessObject.class);

    final UserService userService = new UserService(userDataAccessObject);

    @Test
    void getAllUserTest(){
        //Given
        when(userDataAccessObject.findAll()).thenReturn(UserSeeder.getStockUser());

        //When
        List<User> allUser = userService.getUserList();

        //Then
        assertThat(allUser, containsInAnyOrder(UserSeeder.getStockUser().toArray()));
    }

    @Test
    void addUserTest(){
        //Given
        AddUserDataTransferObject addUserDataTransferObject = new AddUserDataTransferObject(
                "Klaus",
                "Mayer",
                "km@gmail.com"
        );

        User expectedUser = new User(
                null,
                "Klaus",
                "Mayer",
                "km@gmail.com");

        when(userDataAccessObject.save(expectedUser)).thenReturn(expectedUser);

        //When

        User newUser = userService.addUser(addUserDataTransferObject);

        //Then
        assertThat(newUser, is(expectedUser));
        verify(userDataAccessObject).save(expectedUser);
    }

    @Test
    @DisplayName("The \"updateUser\" method should return the updated User object")
    void updateUserTest(){
        //Given
        Integer userID = 12345;

        User userToBeUpdated = new User(
                userID,
                "Klaus",
                "Mayer",
                "km@gmail.com"
        );

        UpdateUserDataTransferObject updateUserDataTransferObject = new UpdateUserDataTransferObject(
                userID,
                "Klaus",
                "Mayer",
                "k.mayer@gmail.com"
        );

        User updatedUser = new User(
                userID,
                "Klaus",
                "Mayer",
                "k.mayer@gmail.com"
        );

        //When
        when(userDataAccessObject.findById(userID)).thenReturn(Optional.of(userToBeUpdated));
        when(userDataAccessObject.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updateUserDataTransferObject);

        //Than
        assertThat(result, is(updatedUser));
        verify(userDataAccessObject).save(updatedUser);
    }

    @Test
    @DisplayName("The \"updateUser\" method should return HttpStatus \"NOT FOUND\"")
    void updateUserThrowsException(){
        //Given
        Integer userID = 12345;

        UpdateUserDataTransferObject updateUserDataTransferObject = new UpdateUserDataTransferObject(
                userID,
                "Klaus",
                "Mayer",
                "k.mayer@gmail.com"
        );

        User updatedUser = new User(
                userID,
                "Klaus",
                "Mayer",
                "k.mayer@gmail.com"
        );

        //When
        when(userDataAccessObject.findById(userID)).thenReturn(Optional.empty());
        when(userDataAccessObject.save(updatedUser)).thenReturn(updatedUser);

        //Than
        try {
            userService.updateUser(updateUserDataTransferObject);
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    void deleteUser(){
        //Given
        Integer userID = 12345;

        User userToBeDeleted = new User(
                userID,
                "Klaus",
                "Mayer",
                "k.mayer@gmail.com"
        );

        //When
        when(userDataAccessObject.findById(userID)).thenReturn(Optional.of(userToBeDeleted));
        userService.deleteUser(userID.toString());

        //Then
        verify(userDataAccessObject).deleteById(userID);
    }

    @Test
    @DisplayName("The \"deleteUser\" method should return HttpStatus \"NOT FOUND\"")
    void deleteUserThrowsException(){
        //Given
        Integer userID = 12345;

        User userToBeDeleted = new User(
                userID,
                "Klaus",
                "Mayer",
                "k.mayer@gmail.com"
        );

        //When
        when(userDataAccessObject.findById(userID)).thenReturn(Optional.empty());

        //Then
        try {
            userService.deleteUser(userID.toString());
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

}
