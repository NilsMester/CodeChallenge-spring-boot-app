package de.teclead.spring_boot_app.service;

import de.teclead.spring_boot_app.data_access_object.UserDataAccessObject;
import de.teclead.spring_boot_app.model.User;
import de.teclead.spring_boot_app.seeder.UserSeeder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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



}
