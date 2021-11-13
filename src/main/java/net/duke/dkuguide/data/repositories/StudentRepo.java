package net.duke.dkuguide.data.repositories;

import net.duke.dkuguide.data.models.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepo extends CrudRepository<Student, Long> {

    Optional<Student> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
