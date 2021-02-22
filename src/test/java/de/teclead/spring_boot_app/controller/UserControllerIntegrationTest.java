package de.teclead.spring_boot_app.controller;

import de.teclead.spring_boot_app.data_access_object.UserDataAccessObject;
import de.teclead.spring_boot_app.data_transfer_object.AddUserDataTransferObject;
import de.teclead.spring_boot_app.data_transfer_object.UpdateUserDataTransferObject;
import de.teclead.spring_boot_app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Profile("development")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserDataAccessObject userDataAccessObject;

    private String getUserUrl() {
        return "http://localhost:" + port + "/api/user";
    }

    @Test
    @Transactional
    public void getUserListTest() {
        //Given
        List<User> stockUser = new ArrayList<>(List.of(
                new User(1, "Max", "Mustermann", "mm@gmail.com"),
                new User(2, "John", "Doe", "johnny@gmail.com"),
                new User(3, "Jaine", "Doe", "j.doe@gmail.com"),
                new User(4, "Max", "Mayer", "m.mayer@gmail.com")
        ));

        String url = getUserUrl();

        //When
        ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(stockUser.toArray()));
    }

    @Test
    @Transactional
    public void postUserShouldAddANewUser() {
        // GIVEN
        String url = getUserUrl();
        AddUserDataTransferObject addUserDataTransferObject = new AddUserDataTransferObject(
                "Roland",
                "Kaiser",
                "Roland@gmail.com"
        );

        // WHEN
        HttpEntity<AddUserDataTransferObject> entity =new HttpEntity<>(addUserDataTransferObject);
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST,entity, User.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new User(
                5, "Roland", "Kaiser", "Roland@gmail.com"
        )));
    }

    @Test
    @Transactional
    public void updateUserShouldUpdateExistingUser(){
        //Given
        String url = getUserUrl() + "/4";

        UpdateUserDataTransferObject userUpdate = new UpdateUserDataTransferObject(
                4, "Max", "Mayer", "max@gmail.com"
        );

        //When
        HttpEntity<UpdateUserDataTransferObject> entity = new HttpEntity<>(userUpdate);
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, entity, User.class);

        //Then
        Optional<User> savedUser = userDataAccessObject.findById(4);

        User expectedUser = new User(
                4, "Max", "Mayer", "max@gmail.com"
        );

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expectedUser));
        assertThat(savedUser.get(), is(expectedUser));
    }

    @Test
    @Transactional
    public void updateUserShouldReturnBadRequest(){
        //Given
        String url = getUserUrl() + "/4";

        UpdateUserDataTransferObject userUpdate = new UpdateUserDataTransferObject(
                5, "Max", "Mayer", "max@gmail.com"
        );

        //When
        HttpEntity<UpdateUserDataTransferObject> entity = new HttpEntity<>(userUpdate);
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, entity, User.class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @Transactional
    public void deleteUserByIdTest(){
        //Given
        String url = getUserUrl() + "/3";

        //When
        restTemplate.delete(url);

        //Then
        Optional<User> deletedUser = userDataAccessObject.findById(3);
        assertThat(deletedUser.isEmpty(), is(true));
    }

    @Test
    @Transactional
    public void deleteUserShouldReturnNotFound(){
        //Given
        String url = getUserUrl() + "/25";

        //When
        HttpEntity<Void> entity = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    @Transactional
    public void findAllByFirstName(){
        //Given
        String url = getUserUrl() + "/Max";

        List<User> stockUserWithFirstNameMax = new ArrayList<>(List.of(
                new User(1, "Max", "Mustermann", "mm@gmail.com"),
                new User(4, "Max", "Mayer", "m.mayer@gmail.com")
        ));

        //When
        ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(stockUserWithFirstNameMax.toArray()));
    }










}
