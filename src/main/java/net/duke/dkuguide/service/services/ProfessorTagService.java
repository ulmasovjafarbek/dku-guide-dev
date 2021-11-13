package net.duke.dkuguide.service.services;

import net.duke.dkuguide.data.models.ProfTagEnum;
import net.duke.dkuguide.data.models.ProfessorTag;

import java.util.Set;

public interface ProfessorTagService {

    Set<ProfessorTag> findTagsByStringNames(Set<String> stringNames);
}
