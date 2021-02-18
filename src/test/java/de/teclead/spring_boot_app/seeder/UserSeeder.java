package de.teclead.spring_boot_app.seeder;

import de.teclead.spring_boot_app.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserSeeder {

    private static final  List<User> user = new ArrayList<>(List.of(
            new User(1, "James", "Cole", "j.cole@gmail.com"),
            new User(2, "Max", "Mustermann", "mm@gmail.com"),
            new User(3, "John", "Doe", "johnny@gmail.com"),
            new User(4, "Jaine", "Doe", "j.doe@gmail.com"),
            new User(5, "James", "Doe", "j.doe@gmail.com")

    ));

    public static List<User> getStockUser(){return user;}
}
