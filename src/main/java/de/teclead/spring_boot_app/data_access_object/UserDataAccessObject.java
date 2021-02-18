package de.teclead.spring_boot_app.data_access_object;

import de.teclead.spring_boot_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataAccessObject extends JpaRepository<User,Integer> {

    List<User> findAll();

}
