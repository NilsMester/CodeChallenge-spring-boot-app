package de.teclead.spring_boot_app.data_access_object;

import de.teclead.spring_boot_app.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDataAccessObject extends CrudRepository<User,Integer> {

    List<User> findAll();

}
