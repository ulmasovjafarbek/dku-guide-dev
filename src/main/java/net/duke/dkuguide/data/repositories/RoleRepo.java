package net.duke.dkuguide.data.repositories;

import net.duke.dkuguide.data.models.UserRole;
import net.duke.dkuguide.data.models.UserRoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends CrudRepository<UserRole, Integer> {
    Optional<UserRole> findByName(UserRoleEnum name);
}
