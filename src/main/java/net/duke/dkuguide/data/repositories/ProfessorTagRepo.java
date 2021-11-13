package net.duke.dkuguide.data.repositories;

import net.duke.dkuguide.data.models.ProfessorTag;
import net.duke.dkuguide.data.models.ProfTagEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorTagRepo extends CrudRepository<ProfessorTag, Integer> {

    ProfessorTag findByTagName(ProfTagEnum tagName);
}
