package net.duke.dkuguide.service.implement;

import net.duke.dkuguide.data.models.ProfTagEnum;
import net.duke.dkuguide.data.models.ProfessorTag;
import net.duke.dkuguide.data.repositories.ProfessorTagRepo;
import net.duke.dkuguide.service.services.ProfessorTagService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProfessorTagServiceImpl implements ProfessorTagService {

    ProfessorTagRepo tagRepo;

    public ProfessorTagServiceImpl(ProfessorTagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public Set<ProfessorTag> findTagsByStringNames(Set<String> stringNames) {
        Set<ProfessorTag> profTags = new HashSet<>();

        for (ProfTagEnum tag : ProfTagEnum.values()) {
            if (stringNames.contains(tag.toString())) {
                profTags.add(tagRepo.findByTagName(tag));
            }
        }

        return profTags;
    }
}
