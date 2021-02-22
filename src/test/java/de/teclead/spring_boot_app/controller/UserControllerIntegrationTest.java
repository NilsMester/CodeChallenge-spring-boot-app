package de.teclead.spring_boot_app.controller;

import de.teclead.spring_boot_app.data_access_object.UserDataAccessObject;
import de.teclead.spring_boot_app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    public void setubDatabase() {
        /*userDataAccessObject.deleteAll();
        userDataAccessObject.saveAll(
                List.of(
                        new User(1, "James", "Cole", "j.cole@gmail.com"),
                        new User(2, "Max", "Mustermann", "mm@gmail.com"),
                        new User(3, "John", "Doe", "johnny@gmail.com"),
                        new User(4, "Jaine", "Doe", "j.doe@gmail.com"),
                        new User(5, "James", "Doe", "j.doe@gmail.com")
                ));*/
    }

    private String getUserUrl() {
        return "http://localhost:" + port + "/api/user";
    }

    @Test
    public void getMappingTest() {
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


}
